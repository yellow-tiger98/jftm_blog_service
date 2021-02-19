#!/bin/sh

cd `dirname $0`/../target
target_dir=`pwd`

pid=`ps ax | grep -i 'jftmBlog' | grep ${target_dir} | grep java | grep -v grep | awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No jftmBlog running."
        exit -1;
fi

echo "The jftmBlog(${pid}) is running...."

kill ${pid}

echo "Send shutdown request to jftmBlog(${pid}) OK"
