#!/usr/bin/env sh

set -x
java -jar ./build/libs/common-interests-network-0.0.1-SNAPSHOT.jar > logs &
sleep 1
echo $! > .pidfile
set +x
