# This script is used to copy all the files in the local windows lib directory to the "lib" directory
# specified in the JAVA_HOME environment variable. If a file with the same name already exists in the
# JAVA_HOME "lib" directory, the script will only copy the file if it has a more recent LastWriteTime.

# Check if the JAVA_HOME environment variable is set
if ($null -eq $env:JAVA_HOME)
{
    Write-Output "The JAVA_HOME environment variable does not exist."
    exit 1
}

# Check if the JAVA_HOME environment variable points to a valid directory that contains a "lib" subdirectory
if (!(Test-Path ($env:JAVA_HOME + "\bin")))
{
    Write-Output "JAVA_HOME is not a valid directory"
    exit 1
}

# Get a list of file in local lib dir
$libFiles = Get-ChildItem "./../../../../../libWindowsJava"

# Get the path to the "lib" directory specified in the JAVA_HOME environment variable
$javaHomeLibDir = $env:JAVA_HOME + "\bin"

foreach ($file in $libFiles)
{
    # Construct the destination file path for the current file
    $destination = Join-Path -Path $javaHomeLibDir -ChildPath $file.Name

    # Check if a file with the same name already exists in the JAVA_HOME "lib" directory
    if (!(Test-Path -Path $destination))
    {
        Write-Output "Copy missing file $file"
        Copy-Item -Path $file.FullName -Destination $javaHomeLibDir
    }
    else
    {
        # If the file already exists in the JAVA_HOME "lib" directory, check if it is older than the file in the
        # local lib directory
        if ($file.LastWriteTime -gt (Get-Item -Path $destination).LastWriteTime)
        {
            Write-Output "Copy more recent file $file"
            Copy-Item -Path $file.FullName -Destination $javaHomeLibDir
        }
    }
}

# Exit with success
exit 0