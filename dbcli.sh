if [[ $1 == "delete" ]]; then
    docker stop poli-auth-datomic || echo 'No container to stop...'
	docker rm poli-auth-postgresql || echo 'No container to remove...'
elif [[ $1 == "start" ]]; then
	docker start poli-auth-datomic
elif [[ $1 == "stop" ]]; then
	docker stop poli-auth-datomic  || echo 'No container to stop...'
elif [[ $1 == "setup" ]]; then
	docker stop poli-auth-datomic || echo 'No container to stop...'
	docker rm poli-auth-datomic || echo 'No container to remove...'
	echo "Creating new container now..."
	if [[ $2 != "" ]]; then
		docker run --name poli-auth-datomic -p 4334-4336:4334-4336 -e ALT_HOST=$2 -d akiel/datomic-free:0.9.5407;
	else
		docker run --name poli-auth-datomic -p 4334-4336:4334-4336 -d akiel/datomic-free:0.9.5407;
	fi
	echo "Container created succesfully"
else
	echo "Choose one of the following options:"
	echo "delete  -> Remove any existing instances of the database"
	echo "start  -> Start a existing instance of the database, or create one if none exists"
	echo "stop -> Stop a running instance of the database"
	echo "setup -> Wipe and create a new database"
fi