#!/bin/sh

declare -a apiservers=("10.12.196.111" "10.12.196.112" "10.12.196.113" "10.12.196.114" "10.12.196.115" "10.12.196.116")
declare -a servernames=("API SERVER 01" "API SERVER 02" "API SERVER 03" "API SERVER 04" "API SERVER 05" "API SERVER 06")

serverlength=${#apiservers[@]}

echo ""

for (( i=1; i<${serverlength}+1; i++));
do
        targetpath="/jb_log/api$((i-1))3/server.log*"
        tput setaf 5
        tput bold
        echo "Start searching logs for \"$1\" at" ${servernames[$i-1]} "-" ${apiservers[$i-1]}
        echo ""
        tput sgr0
        tput setaf 3

        if [ -n "$2" ]
        then
                ssh jboss@${apiservers[$i-1]} "grep -H -n -m $2 $1 $targetpath"
        else
                ssh jboss@${apiservers[$i-1]} "grep -A 56 -H -n $1 $targetpath"
        fi

        if [ $? -eq 0 ]
        then
                tput setaf 2
                tput bold
                echo ""
                echo "[ Found ]"
                echo ""
                tput sgr0

                if [ "$2" != "" ]
                then
                        exit 0
                fi
        else
                tput setaf 1
                tput bold
                echo ""
                echo "[ Not Found ]"
                tput sgr0
        fi

        tput sgr0

        echo ""
done

echo ""
tput sgr0
