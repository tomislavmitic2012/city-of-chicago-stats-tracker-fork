# chicago-stats-tracker-front-end-poc

To create an ssl key at the command prompt type the following:

  1. openssl genrsa -out key.pem
  2. openssl req -new -key key.pem -out csr.pem
  3. openssl x509 -req -days 9999 -in csr.pem -signkey key.pem -out cert.pem
  4. rm csr.pem

Enter the following into your /etc/hosts file

  127.0.0.1 chicagostatstracker.com

To start the node server with javascript expanded

  1. npm install
  2. sudo node server.js expanded

To start the node server with javascript minified

  First minify the javascript and css files

    1. npm install -g uglify-js (This only needs to be run the first time, it places the module in your global node /bin path)
    2. npm install -g uglifycss (This only needs to be run the first time, it places the module in your global node /bin path)
    3. ./minify.sh (uses uglifyjs and uglifycss to uglify css and javascript files)
    4. npm install
    5. sudo node server.js minified

IMPORTANT (BEFORE YOU DO ANY OF THE ABOVE)

I highly recommend using nvm to run node, follow the following instructions:

  1. curl https://raw.githubusercontent.com/creationix/nvm/v0.23.2/install.sh | bash
  2. nvm install 0.11.16
  3. in .nvm/nvm.sh add the following lines:

	alias node='$NVM_BIN/node'  
	alias npm='$NVM_BIN/npm'  

  4. in .bashrc add the following lines of code:

	export NVM_DIR="/home/tomislav/.nvm"  
	[ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  
	. ~/.nvm/nvm.sh  
	alias sudo='sudo '
