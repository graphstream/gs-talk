
cmake_minimum_required (VERSION 2.6)


project (Me)

add_subdirectory (netstream)


add_executable (me  me.cpp)


if(WIN32)
    set(ADDITIONAL_LIBRARIES wsock32)
else()
    set(ADDITIONAL_LIBRARIES "")
endif()

target_link_libraries(me LINK_PUBLIC netstream ${ADDITIONAL_LIBRARIES})
