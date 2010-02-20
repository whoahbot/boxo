require 'socket'
require 'benchmark'

Benchmark.bm(50) do |x|
  @sock = TCPSocket.new("127.0.0.1", "2323")
  @sock.setsockopt Socket::IPPROTO_TCP, Socket::TCP_NODELAY, 1
  
  x.report("Create 10000 k/v pairs") do 
    10000.times do |n|
      @sock.write("SET #{n} #{n}\r\n")
    end
  end
  
  x.report("Increment a key 10000 times") do 
    10000.times do |n|
      @sock.write("INCR 1\r\n")
    end
  end
end