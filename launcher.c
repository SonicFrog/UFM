#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
 * Launcher for Unilabs Fleet Manager pour windows.
 * Detects java version and location and runs Unilabs Fleet Manager
 * Warns the user about the absence of Java when it is required
 * https://github.com/SonicFrog/UFM
 * © 2012 Ogier Bouvier <ogier.bouvier@epfl.ch>
 **/

#define JAVA 			"JAVA_HOME" 						//Name of the environment variable containing Java's path
#define JAVA_EXEC 		"bin\\java.exe"						//Path to the java exec from JAVA_HOME
#define JAVA_REG_KEY 		"SOFTWARE\\JavaSoft\\Java Runtime Environment\\" 	//Registry key containing Java related informations
#define ROOT_HKEY 		HKEY_LOCAL_MACHINE

#define LOG_FILE		"error.log"

#define BUFFER_SIZE 		512

void NoJavaError();
int detectRegistryJava(char* buffer);
int GetStringRegKey(HKEY hKey, const char *strValueName, char* strValue);

int main(int argc, char** argv) {
	char java[MAX_PATH];
	char* java_final_path = NULL;	
	char** args = NULL;
	int javaPathLength = 0;	
	int fd;
	int exitCode;

	freopen(LOG_FILE, "w+", stdout);
	freopen(LOG_FILE, "w+", stderr);

	getenv_s(&javaPathLength, java, MAX_PATH, JAVA); //Reading the JAVA_HOME env variable
	java_final_path = malloc(sizeof(char) * strlen(java));
	
	if(strlen(java_final_path) == 0) { //If the length of JAVA_HOME is 0 = it is not defined
		if(detectRegistryJava(java) < 0) { //We try to detect Java's path using the registry
			NoJavaError(); //If that fails java is not installed and we tell the user
			free(java_final_path); //cleanup
			return EXIT_FAILURE; // and exit with and error code
		}
	}

	strncpy(java_final_path, java, strlen(java));
	java_final_path = realloc(strlen(java_final_path) + 1 + strlen(JAVA_EXEC));
	strncat(java_final_path, JAVA_EXEC, strlen(JAVA_EXEC));
	
	if((fd = _open(java_final_path, O_RDONLY)) < 0) { //Opening the file located at the path we computed for java
		if(errno == ENOENT) { //If it does not exist we warn the user and exit
			NoJavaError();
			free(java_final_path);
			return EXIT_FAILURE;		
		}
	}
	close(fd);
	
	args = malloc(sizeof(char*) * argc - 1); //Copying arguments giving on the command line to the launcher in order to give them to UFM
	for(i = 1 ; i < argc ; i++) {
		arg[i - 1] = argv[i];
	}
	exitCode = execvp(java_final_path, args); //And finally executing UFM... phew!
	free(java_final_path);
	return exitCode;
}

int detectRegistryJava(char* buf) {
	//TODO Function stub to detect java's path from the windows registry if JAVA_HOME is not defined
	return EXIT_FAILURE;
}

int GetStringRegKey(HKEY hKey, const char *strValueName, char* strValue) {
    char szBuffer[BUFFER_SIZE];
    DWORD dwBufferSize = BUFFER_SIZE * sizeof(char);
    int nError;
    nError = RegQueryValueExW(hKey, strValueName, 0, NULL, (LPBYTE)szBuffer, &dwBufferSize);
    if (ERROR_SUCCESS == nError) {
        strncpy(strValue, szBuffer, BUFFER_SIZE);
	return EXIT_SUCCESS;
    }
    return EXIT_FAILURE;
}

void NoJavaError() {
	MessageBox(NULL, "Vous devez installer Java 6 ou supérieur pour utiliser UFM", "Erreur", MB_ERROR | MB_OK);
}
