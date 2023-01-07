# remove all the files inside JAVA_HOME lib directory witch have
# the same name of any file inside libWindowsJava directory

# Check if the JAVA_HOME environment variable exists
if ($null -eq $env:JAVA_HOME)
{
    # If the environment variable does not exist, output an error message and exit the script
    Write-Output "The JAVA_HOME environment variable does not exist."
    exit
}

# Check if the lib directory exists within the JAVA_HOME directory
if (!(Test-Path ($env:JAVA_HOME + "\lib")))
{
    # If the lib directory does not exist, output an error message and exit the script
    Write-Output "JAVA_HOME is not a valid directory"
    exit
}

# Get a list of files in the local lib directory
$libFiles = Get-ChildItem "./../libWindowsJava"

# Get a list of files in the lib directory within JAVA_HOME
$javaHomeLibFiles = Get-ChildItem ($env:JAVA_HOME + "\lib")

# Loop through the local list of files
foreach ($libFile in $libFiles)
{
    # Check if a file with the same name as the current file exists in the JAVA_HOME lib directory
    $javaHomeFile = $javaHomeLibFiles | Where-Object { $_.Name -eq $libFile.Name }
    if ($javaHomeFile)
    {
        # If a matching file was found, output a message indicating that it will be removed, and then remove the file
        Write-Output "Remove file $javaHomeFile"
        Remove-Item $javaHomeFile.FullName
    }
}