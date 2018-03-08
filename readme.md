# Blockchain4EU - CareAi
![Heathcare-data-atm](https://github.com/Lucaslpena/CareAi-blockchain4eu/blob/master/pix/_DSC4342.jpg)
### CareAi
CareAi is a future-looking installation aimed at creating discussion around ideas of:
 * _"How might anonymize healthcare look like on the blockchain?"_
 * _"What if individuals had free access to distributed healthcare databases?"_ 
 * _"How might one share medical data publicly?"_ 
 * _"How might medical feedback be distributed through the blockchain?"_

This project is a collaborative effort of a few individuals:
* [Jordi Planas](http://vimod.net/en/)
* [Gui Seiz](http://cargocollective.com/seiz)
* [Maciej Hirsz](https://github.com/maciejhirsz)
* [Ivo Lõhmus](https://apidaysnordic.github.io/2016/03/23/ivo-lohmus.html)
* [Annalisa Pelizza](https://www.utwente.nl/en/bms/steps/staff/pelizza/)
* [Ideas For Change](https://www.ideasforchange.com/)
* [And Myself](http://lucaslorenzopena.com/)

![Output](https://github.com/Lucaslpena/CareAi-blockchain4eu/blob/master/pix/_DSC4349.jpg)

As part of a join project, in discovering the future of healthcare data ownership, we first need to understand what makes people share their data. For more info on this check out [Triem](https://www.ideasforchange.com/triem/).

### BlockChain4EU

The European Commission's [Joint Research Centre (JRC)](https://ec.europa.eu/jrc/en/about/jrc-in-brief) and the Directorate-General for Internal Market, Industry,
Entrepreneurship & SMEs (DG GROW) have launched the project [#Blockchain4EU](https://twitter.com/hashtag/blockchain4eu?lang=en): Blockchain for Industrial
Transformations, running until February 2018. The project is a forward looking exploration of existing, emerging and
potential applications based on Blockchain and other Distributed Ledger Technologies (DLTs) for non-financial / industry
sectors.

It aims to identify, discuss and communicate possible uses and impacts of Blockchain and other DLT objects, networks and services within EU industrial or business contexts, from supply chains and assets monitoring, to intellectual property rights and authentication or certification. Its main outputs will contribute to explore future socio-technical scenarios of production, distribution and use, and assess risks and opportunities for development and uptake, with a key focus on SMEs and on innovation and competitiveness.

![frontal](https://github.com/Lucaslpena/CareAi-blockchain4eu/blob/master/pix/_DSC4336.jpg)

### Hypothetical Operational Details
The system operates as a network of micro-entrepreneurial owned, automated CareAI Points. Each CareAI Point provides non-hospitalized test and diagnosis to people without access to traditional health care. The CareAI Point interacts with a CareAI Smart Contract running on any smart-contract-enabled blockchain (e.g., Ethereum).

A QR code card representing a private key is used as simple means of authentication to the system, decrypting the medical history of the card holder and uploading new encrypted records for the card holder onto the CareAI Smart Contract. A recurring user scans their QR code at the CareAI Point, while a new user could generate a new private key and get their card printed out during the first visit.

A 3rd party, in our case a health care authority, can issue a payment to the CareAI Smart Contract, which will then allow it to decrypt a number of records collected by different CareAI Points (in a First-in First-out fashion) and redistribute the payment to those CareAI Points to cover the costs and provide an economical incentive for their maintainers.

To foster the growth of the network the Initiator of the program, a research spin-off that can provide Lab-on-a-chip (LOC) technology, can run an Initial Coin Offering (ICO) to fund the research and development of the CareAI Point Open Hardware and Software specification, the Smart Contract code and the data specification.

### Project:
**CareAi** is build using: 
* Processing 3
* Arduino Uno
* Raspbian on a RaspberryPi 3
* Adafruit Thermal Printer
* Light sensors & LEDs
* and 🤖 tech love ❤️ from Barcelona and the 🇪🇺

![pi](https://github.com/Lucaslpena/CareAi-blockchain4eu/blob/master/pix/IMG_4092.jpg)
![arduino](https://github.com/Lucaslpena/CareAi-blockchain4eu/blob/master/pix/IMG_4096.jpg)

### Setup:
#### Materials:
##### Structure:
* 1x ATM Case

##### Hardware:
* 1x Raspberry Pi 3
* 1x Monitor Display
* 1x Arduino Uno + Usb Cable
* 1x Thermal Printer
* 1x USB Mouse

##### Other:
* 2x Printer paper rolls
* 4x ID Cards
* 5x Blood Tests

#### Installation:
* Put the monitor inside the structure and place it against the frame.
* Connect all the power supplies to the 4x plug adaptor ( monitor's power cable and Raspberry Pi micro USB cable), then plug the power extension to 220 AC.
* Connect the VGA-HDMI cable from monitor VGA input to Raspberry HDMI output, and wait until PC completely boots. The app should run automatically.
*For avoiding system sleep mode please plug a mouse and a keyboard to Raspberry USB inputs. Press ESCAPE key to shut down the app, then go to main menu panel and start Screensaver app by agreeing to the first pop-up window. Then doubleclick the BC2 app in the desktop folder for resetting the app.

### Replacing consumables:
2.25" wide, 50 ft or shorter thermal paper:
* [Amazon](https://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Doffice-products&field-keywords=thermal+paper+2%2C25+50&rh=n%3A1064954%2Ck%3Athermal+paper+2%5Cc25+50)
* [Adafruit](https://www.adafruit.com/product/599)
