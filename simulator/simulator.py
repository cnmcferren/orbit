# currently sends the same test packet from the flatsat once every 5 seconds
# sends over UDP to the host and port specified 

import io
import socket
import sys
from time import sleep

filename = 'packet.dat'
host = 'localhost'
port = 10015

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
f = open(filename)
contents = f.read()
count = 0

print("orbit packet simulator")
print("sending packets over UDP every 5 seconds")

while True:
    hexData = contents[:]
    pBytes = bytearray(98)
    # construct a byte array of the packet 
    for x in (range(98)):
        temp = hexData[0:2]
        if temp != '':
            pBytes[x] = int(temp, 16)
        else: 
            pBytes[x] = 0
        hexData = hexData[2:]
    sock.sendto(pBytes, (host, port))
    count += 1
    print(str(count) + " packet(s) sent to " + host + ":" + str(port), end="\r")
    sleep(5)