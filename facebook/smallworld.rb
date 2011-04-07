require 'scanf.rb'

def dist(p1, p2)
  a = p1[0] - p2[0]
  b = p1[1] - p2[1]
  return (a * a) + (b * b)
end

def build_tree(points, depth = 0)
  unless points.empty?
    axis = depth % 2
    points = points.sort { |a, b| a[axis] <=> b[axis] }
    median = points.size / 2
    return {
      :point => points[median],
      :left  => build_tree(points[0, median], depth+1),
      :right => build_tree(points[median+1, points.size],
                           depth+1),
    }
  end
end

def nearest_k(node, point, k, min = [],
              k_dist = Float::MAX, depth = 0)
  if !node.nil?
    axis = depth % 2
    d = point[axis] - node[:point][axis]
 
    # Determine the near and far branches
    near = d <= 0 ? node[:left] : node[:right]
    far = d <= 0 ? node[:right] : node[:left]
 
    # Explore the near branch
    min, k_dist = nearest_k(near, point, k, min, k_dist, depth+1)
 
    # If necessary, explore the far branch
    if d * d < k_dist
      min, k_dist = nearest_k(far, point, k, min, k_dist, depth+1)
    end
 
    # Save the current point as a candidate if it's eligible
    d = dist(point, node[:point])
    if d < k_dist
 
      # Do a binary search to insert the candidate
      i, j = 0, min.size
      while i < j
        m = (i + j) / 2
        if min[m][1] < d
          i = m + 1
        else
          j = m
        end
      end
      min.insert(i, [node[:point], d])
 
      # Keep only the k-best candidates
      min = min[0, k]
 
      # Keep track of the radius of the "best estimates" circle
      k_dist = min[min.size - 1][1] if min.size >= k
    end
  end
  return min, k_dist
end

#!/usr/bin/ruby
friends = [
]

f = File.open("smallworld_test2", "r")
f.each_line do |line|
  arr = line.scanf("%d %f %f")
  friends << [arr[2], arr[1], arr[0]]
end
 
k = 4
tree = build_tree(friends)
friends.each do |f|
  near, d = nearest_k(tree, f, k)
  puts "#{f[2]} " + near[1, k].map { |n| n[0][2] }.join(',')
end