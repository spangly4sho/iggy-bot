echo cleaning docker environment

docker-containers () {
  echo ''
}

docker stop $(docker ps -aq)

sleep 5

docker rm $(docker ps -aq)

sleep 5

docker rmi $(docker images -q)

sleep 5

echo done cleaning docker environment