#!/bin/bash

# This function gets the maximum amount of words that can be tested
maxWords(){
  # Parameters:
    # $1 - filename of chosen topic
  # Returns:
    # maxWords - maximum number of words to be tested
  local file=$1
  local quizType=$2
  local maxWords=5 # 5 is usual max but can be less as some topics have less than 5 words in the word list
  local NUM_WORDS=`cat $file | wc -l`

  if [[ $quizType == "practice" ]]; then
    maxWords=$NUM_WORDS;
  elif [[ $quizType == "test" ]] && [[ $NUM_WORDS -lt 5 ]]; then
    maxWords=$NUM_WORDS
  fi

  return $maxWords
}

# This functions gets list of test words from file and stores them in src/script/tempWords
getTestWords(){
  # Parameters:
    # $1 - filename of chosen topic
    # $2 - maximum number of words to be tested
	local file=$1
  local maxWords=$2

	rm src/script/tempWords

  # Uses maxWords() to determine number of words to generate and randomWords() to generate the words
	for (( i = 1; i<=$maxWords ; i++ )); do
		randomWord $file
	done
}

# This functions generates a random word from the word list file
randomWord(){
  # Parameters:
    # $1 - filename of chosen topic
	local file=$1

  # Generating a random word from given word list
  local NUM_WORDS=`cat $file | wc -l`
	local randNum=$((1 + $RANDOM % $NUM_WORDS))
  local randWord=`sed "${randNum}q;d" $file`

  # Checking if word is already contained in test list, will generate until unique
	while grep -qxF "$randWord" src/script/tempWords; do
		randNum=$((1 + $RANDOM % $NUM_WORDS))
		randWord=`sed "${randNum}q;d" $file`
	done

  echo "$randWord" >> src/script/tempWords
}

# This functions checks the users attempt against the actual spelling
checkSpelling(){
  # Parameters:
    # $1 - number of the word being tested (current progress in game)
    # $2 - users spelling attempt
    # $3 - current attempt number (1 or 2)
  # Returns:
    # exit status (1,2,3,4) - refers to certain correct/incorrect on 1/2 attempt
	local testWordNumber=$1
	local attempt=$2
	local attemptNum=$3


	local actual=`sed "${testWordNumber}q;d" src/script/tempWords`
	local actualUnderscored=${actual// /_} # Allows for spaces
	shopt -s nocasematch # Case insensitive checking


	if [[ $attempt == $actualUnderscored ]] && [ $attemptNum -eq 1 ]; then
		return 1 # correct on first go
	elif [[ $attempt != $actualUnderscored ]] && [ $attemptNum -eq 1 ]; then
		return 2 # incorrect on first go
	elif [[ $attempt == $actualUnderscored ]] && [ $attemptNum -eq 2 ]; then
		return 3 # correct on second go
	elif [[ $attempt != $actualUnderscored ]] && [ $attemptNum -eq 2 ]; then
		return 4 # incorrect on second go
	fi
}




option=$1
wordNum=$2 # Current progress through game
attemptNumber=$3 # Current attempt (1/2)

case $option in
	"getWords" )
		# Generates the random test words from the chosen topic and stores in src/script/tempWords
    topic=$2 # User's chosen topic
    quizType=$3
    touch src/script/tempWords

    maxWords $topic $quizType
		maxWordCount=$?
		getTestWords $topic $maxWordCount
	;;
	"play" )
  	# Obtains and then plays the current test word
  	word=`sed "${wordNum}q;d" src/script/tempWords`
    playbackSpeed=$4

    # Will play once for first attempt and twice for second attempt
  	for (( i = 0; i < $attemptNumber; i++ )); do
  		echo "(voice_akl_mi_pk06_cg) (Parameter.set 'Duration_Stretch "$playbackSpeed") (SayText \""$word"\")" | festival --pipe
  	done
	;;
	"wordCheck" )
		# Checks users attempt with actual spelling
		# Returns echo of exit status referring to words correctness status
		spellingAttempt="$4" # User's spelling attempt
		checkSpelling $wordNum "$spellingAttempt" $attemptNumber
		wordStatus=$?
		echo "$wordStatus"
	;;
	"hint" )
		# Retrieves second letter from word and returns
		# Returns echo of letter to be displayed as a hint upon incorrect attempt
		actual=`sed "${wordNum}q;d" src/script/tempWords`
		echo ${actual:1:1}
	;;
  "getMaxWords" )
    maxNum=`cat src/script/tempWords | wc -l | sed 's/ //g'`
    echo "$maxNum"
  ;;
esac
