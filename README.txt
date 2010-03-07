Build environment
=================

Works for me, and hopefully useful to rebuilt same environment in case
something breaks.

- Ubuntu-9.1 x64 on AMD
- Eclipse Pulsar Galileo (3.5) SR2 for Linux GTK x64
- Sun WTK 2.5.2 tools
- Nokia E52

- bluetooth adaptor on desktop machine (optional)

Fixes
-----
Problem with running emulator because it's target is 32-bit platform. Two
things to provide:

1 - 32-bit JDK (don't forget to update the paths in scripts like ktoolbar)
2 - compatibility libraries from OS (apt-get install ia32-libs)

http://videmos.blogspot.com/2009/03/installing-java-wireless-toolkit-on.html


Other useful links:

http://www.siltala.net/2007/07/25/mounting-a-nokia-phone-a-little-bit-easier/

