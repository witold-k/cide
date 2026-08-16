SUMMARY = "json-c"
LICENSE = "CLOSED"
MODULE = "base/json-c"
SRCPV = "1"

inherit simplecmake encapsulate

python do_patch() {
    import patcher

    s = d.getVar('S')

    p = patcher.Patcher(s + '/CMakeLists.txt', None)
    p.replace('cmake_minimum_required(VERSION 3.9...3.12)', 'cmake_minimum_required(VERSION 3.30)')
    p.write()

    p = patcher.Patcher(s + '/apps/CMakeLists.txt', None)
    p.replace('cmake_minimum_required(VERSION 2.8)', 'cmake_minimum_required(VERSION 3.30)')
    p.write()

    p = patcher.Patcher(s + '/tests/CMakeLists.txt', None)
    p.replace('cmake_minimum_required(VERSION 3.9)', 'cmake_minimum_required(VERSION 3.30)')
    p.write()

    p = patcher.Patcher(s + '/apps/json_parse.c', None)
    p.replace('json_object *new_obj;', '')
    p.write()

    p = patcher.Patcher(s + '/cmake/config.h.in', None)
    p.delete_lines('#cmakedefine HAVE_XLOCALE_H')
    p.write()

}

