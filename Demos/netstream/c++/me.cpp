


#include <iostream>
#include <sstream>
#include "netstream-storage.h"
#include "netstream-socket.h"
#include "netstream-constants.h"
#include "netstream-sender.h"

using namespace std;
using namespace netstream;

int main (int argc, char const *argv[])
{
  string source_id("C++_netstream_test");
  long time_id=0L;
  string server_ip("localhost");

  string id("c++");
  string label("Mr or Ms C++");
  string image("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/C_plus_plus.svg/131px-C_plus_plus.svg.png");

  NetStreamSender stream("default", server_ip, 2001, false);

  stream.addNode(source_id, time_id++, id);
  stream.addNodeAttribute(source_id, time_id++, id, "ui.label", label);
  stringstream st;
  st <<"fill-image: url('"<<image<<"');";
  stream.addNodeAttribute(source_id, time_id++, id, "ui.style", st.str());
  return 0;
}
