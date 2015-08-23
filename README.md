# MyProxy
an Encrypted proxy based on Socks5
A  proxy client and server , for climbing over the gfw.
and because the data of packet is encrypted when climb the GFW,it won't be intercepted by Deep Packet Inspection


##how it works
the program can be run in two mode: inner and outer
the inner one is responsible for encrypt and decrypt the requests from browsers,and forward them to the "outer" one
the outer one is responsible for decrypt the request and send to the target website,and receive response ,encrypt them ,and forwarding to the inner one

##how to use

`java -jar xxx.jar`

if you run the program for the first time ,it will runs in the default mode,and generate a config file "config.json" in the same directory
you can modify the setting and restart the program,your setting will work.

###config.json options:




- **DEBUG**: true or false. the debug mode will runs outer and inner server in the same machine,just for debug.if you 

- **RUN_MODE**: "inner" or "outer". to choose the running mode.

- **OuterServerIP**:only valid in inner mode,
- **OuterServerPort**: valid both in inner mode and outer mode,it determine the port the inner server and outer server to connect.


- **InnerServerPort**:only valid in inner mode.determine the port inner server and broswers connect throught
- ENCRYPT_SEED:the AES encryption seed.the outer and inner should be the same value.


- **BufferSize**:the buffer size of the IO


- **ThreadPoolMode**:"Fixed" or "Cached".the "Fixed" one has a limit the number of threads ,while the "Cached" won't.


- **MaxFixedThreadPoolSize**:only valid if the ThreadPoolMode is Fixed,you can set the maxinum threads of it.


##Example
in your vps,you can run in outer mode as follow
```
{
  "RUN_MODE": "outer",
  "OuterServerIP": "111.111.111.111",
  "OuterServerPort": 2333,
  "InnerServerPort": 1080,
  "ENCRYPT_SEED": "admin",
  "BufferSize": 11024,
  "ThreadPoolMode": "Cached",
  "MaxFixedThreadPoolSize": 1024,
  "DEBUG": false
}
```

in your local pc:
```
{
  "RUN_MODE": "inner",
  "OuterServerIP": "111.111.111.111",
  "OuterServerPort": 2333,
  "InnerServerPort": 1080,
  "ENCRYPT_SEED": "admin",
  "BufferSize": 11024,
  "ThreadPoolMode": "Cached",
  "MaxFixedThreadPoolSize": 1024,
  "DEBUG": false
}
```
at last, you set the socks5 proxy setting in you broswer:127.0.0.1:1080. 
it will work.

