cmake_minimum_required(VERSION 3.12)
project(tmux VERSION 1.0.0 DESCRIPTION "tmux build" LANGUAGES C)

include(GNUInstallDirs)


find_package(Curses   REQUIRED)
find_package(Event    REQUIRED)
find_package(Libsixel REQUIRED)
find_package(M        REQUIRED)

set(srcDir .)

set(srcOsFiles
#    osdep-aix.c
#    osdep-cygwin.c
#    osdep-darwin.c
#    osdep-dragonfly.c
#    osdep-freebsd.c
#    osdep-haiku.c
#    osdep-hpux.c
    osdep-linux.c
#    osdep-netbsd.c
#    osdep-openbsd.c
#    osdep-sunos.c
#    osdep-unknown.c
)

set(srcFiles
    alerts.c
    arguments.c
    attributes.c
    cfg.c
    client.c
    cmd-attach-session.c
    cmd-bind-key.c
    cmd-break-pane.c
    cmd.c
    cmd-capture-pane.c
    cmd-choose-tree.c
    cmd-command-prompt.c
    cmd-confirm-before.c
    cmd-copy-mode.c
    cmd-detach-client.c
    cmd-display-menu.c
    cmd-display-message.c
    cmd-display-panes.c
    cmd-find.c
    cmd-find-window.c
    cmd-if-shell.c
    cmd-join-pane.c
    cmd-kill-pane.c
    cmd-kill-server.c
    cmd-kill-session.c
    cmd-kill-window.c
    cmd-list-buffers.c
    cmd-list-clients.c
    cmd-list-keys.c
    cmd-list-panes.c
    cmd-list-sessions.c
    cmd-list-windows.c
    cmd-load-buffer.c
    cmd-lock-server.c
    cmd-move-window.c
    cmd-new-session.c
    cmd-new-window.c
    cmd-paste-buffer.c
    cmd-pipe-pane.c
    cmd-queue.c
    cmd-refresh-client.c
    cmd-rename-session.c
    cmd-rename-window.c
    cmd-resize-pane.c
    cmd-resize-window.c
    cmd-respawn-pane.c
    cmd-respawn-window.c
    cmd-rotate-window.c
    cmd-run-shell.c
    cmd-save-buffer.c
    cmd-select-layout.c
    cmd-select-pane.c
    cmd-select-window.c
    cmd-send-keys.c
    cmd-server-access.c
    cmd-set-buffer.c
    cmd-set-environment.c
    cmd-set-option.c
    cmd-show-environment.c
    cmd-show-messages.c
    cmd-show-options.c
    cmd-show-prompt-history.c
    cmd-source-file.c
    cmd-split-window.c
    cmd-swap-pane.c
    cmd-swap-window.c
    cmd-switch-client.c
    cmd-unbind-key.c
    cmd-wait-for.c
    colour.c
    control.c
    control-notify.c
    environ.c
    file.c
    format.c
    format-draw.c
    grid.c
    grid-reader.c
    grid-view.c
    hyperlinks.c
    image.c
    input.c
    input-keys.c
    job.c
    key-bindings.c
    key-string.c
    layout.c
    layout-custom.c
    layout-set.c
    log.c
    menu.c
    mode-tree.c
    names.c
    notify.c
    options.c
    options-table.c
    paste.c
    popup.c
    proc.c
    regsub.c
    resize.c
    screen.c
    screen-redraw.c
    screen-write.c
    server-acl.c
    server.c
    server-client.c
    server-fn.c
    session.c
    spawn.c
    status.c
    style.c
    tmux.c
    tty-acs.c
    tty.c
    tty-features.c
    tty-keys.c
    tty-term.c
    utf8.c
    window-buffer.c
    window.c
    window-client.c
    window-clock.c
    window-copy.c
    window-customize.c
    window-tree.c
    xmalloc.c
)

set(srcCompatFiles
    strtonum.c
)

set(publicHeaderFiles "")

list(TRANSFORM srcFiles PREPEND ${srcDir}/)
list(TRANSFORM srcOsFiles PREPEND ${srcDir}/)
list(TRANSFORM srcCompatFiles PREPEND  compat/)
list(TRANSFORM publicHeaderFiles PREPEND ${srcDir}/)


add_executable(tmux ${srcFiles} ${srcOsFiles} ${srcCompatFiles})
target_link_libraries(tmux PUBLIC
  ${CURSES_LIBRARY}
  ${EVENT_LIBRARY}
  SIXEL::SIXEL
  ${M_LIBRARY}
  )
target_include_directories(tmux PUBLIC
  compat
  ${CURSES_INCLUDE_DIRS}
  ${CURSES_INCLUDE_DIRS}/ncursesw
  ${EVENT_INCLUDE_DIRS}
  $[LIBSIXEL_INCLUDE_DIR}
  )


install(TARGETS tmux DESTINATION bin)
