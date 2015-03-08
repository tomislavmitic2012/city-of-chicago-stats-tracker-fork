#!/bin/sh

#if [ ! -d 'js/min/' ]; then mkdir js/min/; echo 'Successfully created directory js/min/'; fi
#if [ ! -d 'js/min/viewmodels/' ]; then mkdir js/min/viewmodels/; echo 'Successfully created directory js/min/viewmodels/'; fi
#if [ ! -d 'js/min/utils/' ]; then mkdir js/min/utils/; echo 'Successfully created directory js/min/utils/'; fi
#if [ ! -d 'js/min/models/' ]; then mkdir js/min/models/; echo 'Successfully created directory js/min/models/'; fi
#if [ ! -d 'js/min/models/cn/' ]; then mkdir js/min/models/cn/; echo 'Successfully created directory js/min/models/cn/'; fi
#if [ ! -d 'js/min/models/en/' ]; then mkdir js/min/models/en/; echo 'Successfully created directory js/min/models/en/'; fi
#if [ ! -d 'js/min/models/jp/' ]; then mkdir js/min/models/jp/; echo 'Successfully created directory js/min/models/jp/'; fi
#if [ ! -d 'js/min/bootstrap/' ]; then mkdir js/min/bootstrap/; echo 'Successfully created directory js/min/bootstrap/'; fi
#if [ ! -d 'js/min/bootstrap/jp/' ]; then mkdir js/min/bootstrap/jp/; echo 'Successfully created directory js/min/bootstrap/jp/'; fi
#if [ ! -d 'js/min/bootstrap/en/' ]; then mkdir js/min/bootstrap/en/; echo 'Successfully created directory js/min/bootstrap/en/'; fi
#if [ ! -d 'js/min/bootstrap/cn/' ]; then mkdir js/min/bootstrap/cn/; echo 'Successfully created directory js/min/bootstrap/cn/'; fi
#if [ ! -d 'js/min/app/' ]; then mkdir js/min/app/; echo 'Successfully created directory js/min/app/'; fi
#if [ ! -d 'js/min/app/jp/' ]; then mkdir js/min/app/jp/; echo 'Successfully created directory js/min/app/jp/'; fi
#if [ ! -d 'js/min/app/en/' ]; then mkdir js/min/app/en/; echo 'Successfully created directory js/min/app/en/'; fi
#if [ ! -d 'js/min/app/cn/' ]; then mkdir js/min/app/cn/; echo 'Successfully created directory js/min/app/cn/'; fi
#if [ ! -d 'css/min/' ]; then mkdir css/min/; echo 'Successfully created directory css/min/'; fi

#if [ '$(ls -A js/min/viewmodels/)' ]; then rm -f js/min/viewmodels/*; echo 'Removed all files from js/min/viewmodels/'; fi
#if [ '$(ls -A js/min/utils/)' ]; then rm -f js/min/utils/*; echo 'Removed all files from js/min/utils/'; fi
#if [ '$(ls -A js/min/models/cn/)' ]; then rm -f js/min/models/cn/*; echo 'Removed all files from js/min/models/cn/'; fi
#if [ '$(ls -A js/min/models/en/)' ]; then rm -f js/min/models/en/*; echo 'Removed all files from js/min/models/en/'; fi
#if [ '$(ls -A js/min/models/jp/)' ]; then rm -f js/min/models/jp/*; echo 'Removed all files from js/min/models/jp/'; fi
#if [ '$(ls -A js/min/bootstrap/jp/)' ]; then rm -f js/min/bootstrap/jp/*; echo 'Removed all files from js/min/bootstrap/jp/'; fi
#if [ '$(ls -A js/min/bootstrap/en/)' ]; then rm -f js/min/bootstrap/en/*; echo 'Removed all files from js/min/bootstrap/en/'; fi
#if [ '$(ls -A js/min/bootstrap/cn/)' ]; then rm -f js/min/bootstrap/cn/*; echo 'Removed all files from js/min/bootstrap/cn/'; fi
#if [ '$(ls -A js/min/app/jp/)' ]; then rm -f js/min/app/jp/*; echo 'Removed all files from js/min/app/jp/'; fi
#if [ '$(ls -A js/min/app/en/)' ]; then rm -f js/min/app/en/*; echo 'Removed all files from js/min/app/en/'; fi
#if [ '$(ls -A js/min/app/cn/)' ]; then rm -f js/min/app/cn/*; echo 'Removed all files from js/min/app/cn/'; fi
#if [ '$(ls -A css/min/)' ]; then rm -f css/min/*; echo 'Removed all files from css/min/'; fi

#uglifyjs -o js/min/app/cn/account-application.js js/app/cn/account-application.js && echo 'Uglified js/app/cn/account-application.js to js/min/app/cn/account-application.js';

#uglifycss css/slidemenu.css > css/min/slidemenu.css && echo 'Uglified css/slidemenu.css to css/min/slidemenu.css';