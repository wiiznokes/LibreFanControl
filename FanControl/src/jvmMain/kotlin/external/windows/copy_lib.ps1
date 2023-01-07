# This script is used to copy all the files in the "./../../../../../lib" directory to the "lib" directory
# specified in the JAVA_HOME environment variable. If a file with the same name already exists in the
# JAVA_HOME "lib" directory, the script will only copy the file if it has a more recent LastWriteTime.

# Check if the JAVA_HOME environment variable is set
if ($null -eq $env:JAVA_HOME) {
    # If JAVA_HOME is not set, print an error message and exit the script with a non-zero exit code
    Write-Output "The JAVA_HOME environment variable does not exist."
    exit 1
}

# Check if the JAVA_HOME environment variable points to a valid directory that contains a "lib" subdirectory
if (!(Test-Path ($env:JAVA_HOME + "\lib"))) {
    # If the "lib" subdirectory does not exist, print an error message and exit the script with a non-zero exit code
    Write-Output "JAVA_HOME is not a valid directory"
    exit 1
}

# Get a list of all the files in the "./../../../../../lib" directory
$libFiles = Get-ChildItem "./../../../../../lib"

# Get the path to the "lib" directory specified in the JAVA_HOME environment variable
$javaHomeLibDir = $env:JAVA_HOME + "\lib"

# Iterate over all the files in the "./../../../../../lib" directory
foreach ($file in $libFiles)
{
    # Construct the destination file path for the current file
    $destination = Join-Path -Path $javaHomeLibDir -ChildPath $file.Name

    # Check if a file with the same name already exists in the JAVA_HOME "lib" directory
    if (!(Test-Path -Path $destination))
    {
        # If the file does not already exist, copy it to the JAVA_HOME "lib" directory
        Write-Output "Copy missing file $file"
        Copy-Item -Path $file.FullName -Destination $javaHomeLibDir
    }
    else
    {
        # If the file already exists in the JAVA_HOME "lib" directory, check if it is older than the file in the
        # "./../../../../../lib" directory
        if ($file.LastWriteTime -gt (Get-Item -Path $destination).LastWriteTime)
        {
            # If the file in the "./../../../../../lib" directory is more recent, copy it to the JAVA_HOME "lib" directory
            Write-Output "Copy more recent file $file"
            Copy-Item -Path $file.FullName -Destination $javaHomeLibDir
        }
    }
}

# Exit the script with a zero exit code
exit 0