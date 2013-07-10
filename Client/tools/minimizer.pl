#!/usr/bin/perl

# Minimizza CSS/HTML/JavaScript di MyTalk e crea un ZIP in /tmp/Client.zip
# Assicurarsi di avere le librerie JavaScript minimizzate ed aggiornate nella folder /assets/lib
# Dare --only-zip per zippare il Client senza minimizzare (debug mode)
#
# Dipendenze del Sistema Operativo UNIX: 
# >> zip (sudo apt-get install zip)
#
# Dipendenze da librerie esterne:
# >> yuicompressor-2.4.8.jar

use warnings;
use strict;

my $PROJECT_DIR = "../";
my $COMPRESSOR = "yuicompressor-2.4.8.jar";

unless (-e "/usr/bin/zip") {
	print "ZIP non è installato nel proprio sistema operativo. Installarlo, per esempio, con sudo apt-get install zip\n";
	exit;
}

unless (-e $COMPRESSOR) {
	print "$COMPRESSOR non trovato nella folder corrente\n";
	exit;
}

print "# Minimizza CSS/HTML/JavaScript di MyTalk e crea un ZIP in /tmp/Client.zip
# Assicurarsi di avere le librerie minimizzate ed aggiornate nella folder /assets/lib
# Dare --only-zip per zippare il Client senza minimizzare (debug mode)\n\n>> Sto lavorando...\n";

if (!defined $ARGV[0]) {
		my $index = `cd $PROJECT_DIR && cat index.html`; $index =~ s/`/'/g;
		my @js_library_file = $index =~ /<script type="text\/javascript" src="(assets\/lib\/.*?)">/g;
		my @js_our_file = $index =~ /<script type="text\/javascript" src="(com\/.*?)">/g;
		my @css_file = $index =~ /<link type="text\/css" rel="stylesheet" media="screen" href="(.*?)"\/>/g;
		my $js_our_content = "";
		my $css_content = "";
		foreach my $i ( @js_our_file ) {
			$js_our_content .= `cd $PROJECT_DIR && cat $i`;
		}

		# add also animation.js
		if( $index =~ /animation\/animation\.js/g ){
			$js_our_content .= `cd $PROJECT_DIR && cat assets/animation/animation.js` 
		}

		foreach my $i ( @css_file ) {
			$css_content .= `cd $PROJECT_DIR && cat $i`;
		}

		#1
		open FILE, ">/tmp/mytalk.js" or die $!;
		print FILE $js_our_content;
		close FILE;
		`java -jar $COMPRESSOR /tmp/mytalk.js > /tmp/app.js`;

		#2
		my $jquery;
		my $tipsy;
		foreach my $i ( @js_library_file ) {
			$jquery = $i if($i =~ /jquery\-/);
			$tipsy = $i if($i =~ /tipsy/);
		}
		my $jt = `cd $PROJECT_DIR && cat $jquery`;
		$jt .= `cd $PROJECT_DIR && cat $tipsy`;
		open FILE, ">/tmp/a.js" or die $!;
		print FILE $jt;
		close FILE;
		########################################################
		my $moment;
		my $handlebars;
		my $mh;
		foreach my $i ( @js_library_file ) {
			$moment = $i if($i =~ /moment/);
			if($i =~ /handlebars/) {
		    $i =~ s/\.js//;
				$handlebars = "$i.min.js"; 
			}
		}
		$mh = `cd $PROJECT_DIR && cat $moment`;
		$mh .= `cd $PROJECT_DIR && cat $handlebars`;
		open FILE, ">/tmp/b.js" or die $!;
		print FILE $mh;
		close FILE;
		########################################################
		my $ember;
		foreach my $i ( @js_library_file ) {
			if($i =~ /ember\-1/) {
		    $i =~ s/\.js//;
				$ember = "$i.min.js"; 
			}
		}
		open FILE, ">/tmp/c.js" or die $!;
		print FILE `cd $PROJECT_DIR && cat $ember`;
		close FILE;
		########################################################
		my $ember_data;
		my $list_view;
		my $base = "";
		my $elb;
		foreach my $i ( @js_library_file ) {
			if($i =~ /ember\-data/) {
				$i =~ s/\.js//;
				$ember_data = "$i.min.js"; 
			}
			$list_view = $i if($i =~ /list\-view/);
			$base = $i if($i =~ /base64/);
		}
		$elb = `cd $PROJECT_DIR && cat $ember_data`;
		$elb .= `cd $PROJECT_DIR && cat $list_view`;
		$elb .= `cd $PROJECT_DIR && cat $base` if($base ne "");
		open FILE, ">/tmp/d.js" or die $!;
		print FILE $elb;
		close FILE;

		#3
		open FILE, ">/tmp/a.css" or die $!;
		print FILE $css_content;
		close FILE;
		`java -jar $COMPRESSOR /tmp/a.css > /tmp/css.css`;

		#4
		my $JS = '<link type="text/css" rel="stylesheet" media="screen" href="assets/css/css.css"/>';
		$JS .= '<script type="text/javascript" src="assets/lib/a.js"></script>';
		$JS .= '<script type="text/javascript" src="assets/lib/b.js"></script>';
		$JS .= '<script type="text/javascript" src="assets/lib/c.js"></script>';
		$JS .= '<script type="text/javascript" src="assets/lib/d.js"></script>';
		$JS .= '<script type="text/javascript" src="assets/lib/app.js"></script>';
		$index =~ s/<link type="text\/css" rel="stylesheet" media="screen" href="(.*?)"\/>//g;
		$index =~ s/<script type="text\/javascript"((.|\t|\n|\r)*?)<\/script>//g;
		$index =~ s/<title>(.*?)<\/title>/<title>$1<\/title>\n$JS/;
		$index =~ s/(\r\n|\n|\r|\t)//g;
		$index =~ s/\s+/ /g;
		$index =~ s/>\s+</></g;
		$index =~ s/<\!\-\-.*?\-\->//g;
		open FILE, ">/tmp/index.html" or die $!;
		print FILE $index;
		close FILE;
	
		#5
		`rm -rf /tmp/Client` if (-e "/tmp/Client");
		`mkdir /tmp/Client && cp -r $PROJECT_DIR /tmp/Client`;
		`rm -rf /tmp/Client/com`;
		`rm -rf /tmp/Client/tools`;
		`rm /tmp/Client/README.md` if (-e "/tmp/Client/README.md");
		`rm /tmp/Client/assets/lib/* && rm /tmp/Client/assets/css/* && cd /tmp && cp a.js Client/assets/lib && cp b.js Client/assets/lib && cp c.js Client/assets/lib && cp d.js Client/assets/lib && cp css.css Client/assets/css && cp app.js Client/assets/lib && cp index.html Client`;

		#6
		`cd /tmp/Client && zip -r /tmp/Client.zip .`;
		`rm -rf /tmp/Client`;
		`cd /tmp && rm index.html mytalk.js app.js a.js b.js c.js d.js a.css css.css`;
	} else {
		`zip -r /tmp/Client .`;
	}
