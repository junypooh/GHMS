#!/bin/sh

declare -a apiservers=("10.12.196.112" "10.12.196.113" "10.12.196.114" "10.12.196.115" "10.12.196.116")
declare -a servernames=("API_SERVER_02" "API_SERVER_03" "API_SERVER_04" "API_SERVER_05" "API_SERVER_06")

serverlength=${#apiservers[@]}

for (( i=1; i<${serverlength}+1; i++));
do
        targetpath="/jboss/applications/api"$i"3/ghms.war"
        tput setaf 3
        echo "Start to copy ghms.war to" ${servernames[$i-1]} "- " ${apiservers[$i-1]}
        tput setaf 2
        echo ""
        tput setaf 1
        rsync -azv ghms.war jboss@${apiservers[$i-1]}:$targetpath >/dev/null

        if [ $? -eq 0 ]; then
                tput setaf 2
                echo "[ OK ]"
                tput sgr0
        else
                tput setaf 1
                echo ""
                echo "[ FAIL ]"
                tput sgr0
                exit 1
        fi
        echo ""
done
tput sgr0
