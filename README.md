# Silverstripe Template Plugin for Netbeans

A NetBeans plugin offering support for SilverStripe template files.

So far it includes:

* Syntax highlighting for *.ss files
* Hyperlinking of files in include and require statements
* Folding for SilverStripe tags
* Rudimentary highlighting of syntax errors

## Requirements

* NetBeans 8.x

## Install

* Choose appropriate release from [releases]
* Download nbm file
* In NetBeans, go to "Tools/Plugins" and choose "Downloaded" tab
* Click "Add plugins..." and add your downloaded nbm file
* Click "Install" and follow along.

[releases]: https://github.com/jdemeschew/stpnb/releases

## Uninstall

* Go to "Tools/Plugins" and choose "Installed" tab
* Type "silverstripe" into "Search:" field
* Check "Silverstripe template plugin" and click "Uninstall"

## Build from source

* **Prerequisites**  
If not already installed, install [JDK] 1.7 or above and [Maven] 3.0 or above
* **Clone**  
    git clone https://github.com/jdemeschew/stpnb.git
* **Build**  
    cd stpnb  
    mvn package  

nbm file will be placed into target/ folder
[JDK]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[Maven]: https://maven.apache.org/install.html
