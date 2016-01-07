add_library (netstream netstream-sender.cpp netstream-socket.cpp netstream-storage.cpp)

target_include_directories (netstream PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
