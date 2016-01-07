from random import randint
from gs_netstream.sender import  NetStreamProxyGraph, NetStreamSender

serverIP = "localhost"

id = "python"
label = "Mr or Ms Python"
image_url = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Python-logo-notext.svg/1024px-Python-logo-notext.svg.png"

sender = NetStreamSender(2001, serverIP)
proxy = NetStreamProxyGraph(sender)

proxy.add_node(id)
proxy.add_node_attribute( id, "ui.label", label)
proxy.add_node_attribute( id, "ui.style", "fill-image: url('"+image_url+"');")
