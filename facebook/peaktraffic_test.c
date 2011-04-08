#include <inttypes.h>
#include <stdio.h>
#include <stdlib.h>

static uint32_t x = 123456789;
static uint32_t y = 362436069;
static uint32_t z = 521288629;
static uint32_t w = 88675123;
static uint32_t v = 886756453;

static uint32_t xorshift(void) {
    uint32_t t;
    t = x ^ (x >> 7);
    x = y;
    y = z;
    z = w;
    w = v;
    v = v ^ (v << 6) ^ t ^ (t << 13);
    return (y + y + 1) * v;
}

int main(int argc, char *argv[]) {
    char buf[32], *endptr;
    unsigned long l;
    uint32_t i, a, b, j, r;
    if(argc != 2 || *argv[1] == '\0') goto usage;
    l = strtoul(argv[1], &endptr, 10);
    if(*endptr != '\0' || l < 2 || 22 < l) goto usage;
    for(i = 0; i < (1UL << (l + 4)) * (l + 4); ++i) {
        a = 0;
        b = 0;
        for(j = 0; j < l; ++j) {
            a <<= 1;
            b <<= 1;
            r = xorshift() % 20;
            if(r < 11) a |= 1;
            if(r < 8 || r >= 18) b |= 1;
        }
        printf("Thu Jan 01 00:00:00 UTC 1970\t%lu@example.com\t%lu@example.com\n", (unsigned long)a, (unsigned long)b);
    }
    return 0;
usage:
    printf(1, "usage: %s logpopulation", argv[0]);
}
