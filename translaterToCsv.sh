#!/bin/bash
sep='---------------'
## declare an array variable
CURRENT_PATH=`pwd`
. $CURRENT_PATH/translater.cfg
if [ -n "$1" ]; then
	echo "Configutation file is $1"
	source $CURRENT_PATH/$1
else
	echo "Configutation file is translater.cfg"
	source $CURRENT_PATH/translater.cfg
fi
downloadFromRepo()
{
	branches[0]=$android_branch
	branches[1]=$ios_branch
	branches[2]=$web_branch

	projectsPath[0]=$android_project_repo
	projectsPath[1]=$ios_project_repo
	projectsPath[2]=$web_project_repo
	counter=0
	for i in "${projectsPath[@]}"
	do
		if [ -n "$i" ]; then
			rm -r $i
			echo "Clone $i"
			d=`echo $i | sed s#/##`                               # remove trailing forward slash
			remoteRepo="https://$username@$repo_url/$repo_name/$i"    # location of remote repo
			branch=${branches[${counter}]}
			echo "Clone from $remoteRepo with branch: $branch"
			git clone -b $branch $remoteRepo  # add remote repo as upstream
			echo -e "\n"
		fi
		let "counter += 1"
	done
}
generateConfig()
{
	echo "Generate config file"
	modulePath=$android_module_path
	rm config.properties
	echo "CSV_PATH=$CURRENT_PATH/csv/">>config.properties
	if [ -n "$android_module_path" ]; then
	   echo "
ANDROID_LOCATION=$CURRENT_PATH/$android_project_repo/$android_module_path/
ANDROID_INCLUDING=$android_including
ANDROID_DEFAULT_LOCATION=$default_language">>config.properties
	fi
	if [ -n "$ios_project_path" ]; then
		echo "
IOS_LOCATION=$CURRENT_PATH/$ios_project_repo/$ios_project_path/
IOS_DEFAULT_LOCATION=$default_language
IOS_CHARSET=$ios_charset">>config.properties
	fi
	if [ -n "$web_project_repo" ]; then
		echo "
WEB_LOCATION=$CURRENT_PATH/$web_project_repo/
WEB_DEFAULT_LOCATION=$default_language">>config.properties
	fi
}
generateCsv()
{
	echo "Generate csv"
	java -jar translater.jar config.properties TO_CSV
	echo -e "\n"
}
removeProjectsFolders()
{
	projectsPath[0]=$android_project_repo
	projectsPath[1]=$ios_project_repo
	projectsPath[2]=$web_project_repo
	for i in "${projectsPath[@]}"
	do
		if [ -n "$i" ]; then
			rm -r $i
		fi
	done
	rm config.properties
}

#downloadFromRepo
generateConfig
generateCsv
removeProjectsFolders
exit $?
