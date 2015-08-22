# MyProxy
an Encrypted proxy based on Socks5
A  proxy client and server , for climbing over the gfw.
and because the data of packet is encrypted when climb the GFW,it won't be intercepted by Deep Packet Inspection


##how it works
the program can be run in two mode: inner and outer
the inner one is responsible for encrypt and decrypt the requests from browsers,and forward them to the "outer" one
the outer one is responsible for decrypt the request and send to the target website,and receive response ,encrypt them ,and forwarding to the inner one

