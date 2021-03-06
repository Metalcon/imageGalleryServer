configFile="installConfig.sh"

# check for installation config
if [ ! -e "$configFile" ]
then
        echo "Installation config not found: $configFile"
        exit
fi

source $configFile
CONFIG_NAME="config.txt"

# check for server config
if [ ! -e "$CONFIG_NAME" ]
then
	echo "image gallery server config not found: \"$CONFIG_NAME\""
	echo "edit \"sample-config.txt\" to match your needs and do"
	echo "cp sample-config.txt $CONFIG_NAME"
	exit
fi

echo "server directory is \"$SERVER_DIR\""
if [ ! -e "$SERVER_DIR" ]
then
	# create server directory
	echo "directory not present, creating..."
	sudo mkdir -p $SERVER_DIR
fi

echo "storage directory is \"$STORAGE_DIR\""
if [ ! -e "$STORAGE_DIR" ]
then
	# create storage directory
	echo "directory not present, creating..."
	sudo mkdir -p $STORAGE_DIR
fi

# set directory rights
sudo chown -R $SERVER_DIR_RIGHTS $SERVER_DIR
sudo chown -R $SERVER_DIR_RIGHTS $STORAGE_DIR
echo "set directory rights to \"$SERVER_DIR_RIGHTS\""

# reset server files
rm -rf $SERVER_DIR/*
echo "server directory cleaned"

cp $CONFIG_NAME $CONFIG_PATH
echo "server config is \"$CONFIG_PATH\""

