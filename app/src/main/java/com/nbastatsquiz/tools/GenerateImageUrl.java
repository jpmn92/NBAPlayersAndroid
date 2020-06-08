package com.nbastatsquiz.tools;

import com.nbastatsquiz.beans.NBAPlayer;

import java.util.ArrayList;
import java.util.Random;

public class GenerateImageUrl {


    public ArrayList<NBAPlayer> getNBAPlayers() {

        ArrayList<NBAPlayer> nbaPlayers = new ArrayList<>();

        nbaPlayers.add(new NBAPlayer(1, "James Harden", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612745/2019/260x190/201935.png"));
        nbaPlayers.add(new NBAPlayer(2, "Giannis Antetokoumpo", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612749/2019/260x190/203507.png"));
        nbaPlayers.add(new NBAPlayer(4, "Lebron James", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2019/260x190/2544.png"));
        nbaPlayers.add(new NBAPlayer(5, "Stephen Curry", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612744/2019/260x190/201939.png"));
        nbaPlayers.add(new NBAPlayer(6, "Luka Doncic", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612742/2019/260x190/1629029.png"));
        nbaPlayers.add(new NBAPlayer(7, "Anthony Davis", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2019/260x190/203076.png"));
        nbaPlayers.add(new NBAPlayer(8, "Steve Nash", "https://i.dlpng.com/static/png/219514_preview.png"));
        nbaPlayers.add(new NBAPlayer(9, "Tim Duncan", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612759/2015/260x190/1495.png"));
        nbaPlayers.add(new NBAPlayer(10, "Michael Jordan", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/893.png"));
        nbaPlayers.add(new NBAPlayer(11, "Kevin Durant", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612751/2019/260x190/201142.png"));
        nbaPlayers.add(new NBAPlayer(12, "Kyrie Irving", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612751/2019/260x190/202681.png"));
        nbaPlayers.add(new NBAPlayer(13, "Kobe Bryant", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2015/260x190/977.png"));
        nbaPlayers.add(new NBAPlayer(14, "Joel Embiid", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612755/2019/260x190/203954.png"));
        nbaPlayers.add(new NBAPlayer(15, "Dwyane Wade", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612748/2018/260x190/2548.png"));
        nbaPlayers.add(new NBAPlayer(16, "Chris Paul", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612760/2019/260x190/101108.png"));
        nbaPlayers.add(new NBAPlayer(17, "Zion Williamson", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612740/2019/260x190/1629627.png"));
        nbaPlayers.add(new NBAPlayer(18, "Karl Anthony Towns", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612750/2019/260x190/1626157.png"));
        nbaPlayers.add(new NBAPlayer(19, "Jimmy Butler", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612748/2019/260x190/202710.png"));
        nbaPlayers.add(new NBAPlayer(20, "Nikola Jokic", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612743/2019/260x190/203999.png"));
        nbaPlayers.add(new NBAPlayer(21, "Paul George", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612746/2019/260x190/202331.png"));
        nbaPlayers.add(new NBAPlayer(22, "Shaquille O´Neal", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/406.png"));
        nbaPlayers.add(new NBAPlayer(23, "Kawhi Leonard", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612746/2019/260x190/202695.png"));
        nbaPlayers.add(new NBAPlayer(24, "Klay Thompson", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612744/2019/260x190/202691.png"));
        nbaPlayers.add(new NBAPlayer(25, "Donovan Mitchell", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612762/2019/260x190/1628378.png"));
        nbaPlayers.add(new NBAPlayer(26, "Buddy Hield", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612758/2019/260x190/1627741.png"));
        nbaPlayers.add(new NBAPlayer(27, "Pau Gasol", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612749/2018/260x190/2200.png"));
        nbaPlayers.add(new NBAPlayer(28, "Peja Stojakovic", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/978.png"));
        nbaPlayers.add(new NBAPlayer(29, "Chris Bosh", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/2547.png"));
        nbaPlayers.add(new NBAPlayer(30, "Metta World Peace", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2016/260x190/1897.png"));
        nbaPlayers.add(new NBAPlayer(31, "Larry Bird", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/1449.png"));
        nbaPlayers.add(new NBAPlayer(32, "Dennis Rodman", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/23.png"));
        nbaPlayers.add(new NBAPlayer(33, "Karl Malone", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/252.png"));
        nbaPlayers.add(new NBAPlayer(34, "Allen Iverson", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/947.png"));
        nbaPlayers.add(new NBAPlayer(35, "Blake Griffin", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612765/2019/260x190/201933.png"));
        nbaPlayers.add(new NBAPlayer(36, "Tony Parker", "https://nba-players.herokuapp.com/players/Parker/Tony"));
        nbaPlayers.add(new NBAPlayer(37, "Manu Ginobili", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612759/2017/260x190/1938.png"));
        nbaPlayers.add(new NBAPlayer(38, "Ben Simmons", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612755/2019/260x190/1627732.png"));
        nbaPlayers.add(new NBAPlayer(39, "Trae Young", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612737/2019/260x190/1629027.png"));


        return nbaPlayers;
    }


    public String checkPlayerImage(int idJugador) {


        String urlImage = "";

        switch (idJugador) {

            //vin baker
            case 452:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Vin-Baker-2K.png";
                break;
            //jonny flynn
            case 201938:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/3985.png";
                break;

            //jerry west
            case 78497:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Jerry-West-2K.png";
                break;
            //vladimir radmanovic
            case 2209:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1016.png";
                break;
            //john starks
            case 317:
                urlImage = "https://www.2kratings.com/wp-content/uploads/John-Starks-2K.png";
                break;
            //ron harper
            case 166:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Ron-Harper-2K.png";
                break;
            //brian cardinal
            case 2073:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/130.png";
                break;
            //linas kleiza
            case 101132:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2770.png";
                break;

            //ryan gomes
            case 101155:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2757.png";
                break;

            //jason maxiell
            case 101131:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2775.png&w=350&h=254";
                break;

            //andray blatche
            case 101154:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/2746.png";
                break;

            //joel przybilla
            case 2038:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/682.png";
                break;

            //reggie evans
            case 2501:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/1828.png";
                break;

            //andris biedrins
            case 2740:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2427.png";
                break;

            //darko milicic
            case 2545:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2171.png";
                break;
            //sebastian telfair
            case 2742:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2417.png&w=350&h=254";
                break;

            //flip murray
            case 2436:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1777.png";
                break;
            //anthony carter
            case 1853:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/137.png";
                break;

            //daniel gibson
            case 200789:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/3006.png&w=350&h=254";
                break;

            //raja bell
            case 1952:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/49.png";
                break;
            //hakim warrick
            case 101124:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2794.png&w=350&h=254";
                break;

            //kwame brown
            case 2198:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/981.png";
                break;

            //matt carroll
            case 2679:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2211.png";
                break;
            //morris peterson
            case 2050:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Morris-Peterson-2K.png";
                break;

            //jamaal magloire
            case 2048:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/498.png";
                break;

            //tony battie
            case 1499:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/45.png&w=350&h=254";
                break;

            //larry hughes
            case 1716:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/356.png";
                break;

            //johan petro
            case 101130:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2780.png";
                break;

            //travis outlaw
            case 2566:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2015.png";
                break;

            //jannero pargo
            case 2457:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1821.png";
                break;

            //aaron mckie
            case 243:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Aaron-McKie-2K.png";
                break;

            //brendan haywood
            case 2217:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1000.png&w=350&h=254";
                break;

            //ricky davis
            case 1729:
                urlImage = "https://big3.com/wp-content/uploads/2018/05/GHOST-BALLERS__RICKY-DAVIS.png";
                break;

            //chucky atkins
            case 1088:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/26.png";
                break;

            //earl watson
            case 2248:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1027.png";
                break;

            //horace grant
            case 270:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Horace-Grant-Chicago-Bulls-2K.png";
                break;

            //keith bogans
            case 2586:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1995.png";
                break;

            //rick fox
            case 296:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Rick-Fox-2K.png";
                break;

            //delonte west
            case 2753:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2422.png";
                break;

            //ronny turiaf
            case 101142:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/2789.png";
                break;

            //carlos delfino
            case 2568:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/1999.png";
                break;

            //rashad mccants
            case 101119:
                urlImage = "https://dev.big3.com/wp-content/uploads/2018/05/TRILOGY_MCCANTS_RASHAD.png";
                break;

            //tyrus thomas
            case 200748:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/3032.png";
                break;

            //josh boone
            case 200767:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2990.png";
                break;

            //kenny smith
            case 181:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Kenny-Smith-2K.png";
                break;

            //nate mcmillan
            case 203:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Nate-McMillian-2K.png";
                break;

            //mehmet okur
            case 2246:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1014.png";
                break;

            //donyell marshall
            case 923:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Donyell-Marshall-2K.png";
                break;

            //byron scott
            case 2:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Byron-Scott-2K.png";
                break;

            //landry fields
            case 202361:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Landry-Fields-2K.png";
                break;

            //luke walton
            case 2575:
                urlImage = "https://s.yimg.com/it/api/res/1.2/OeJFDROgIZ6K2zhKZbL5Yw--~A/YXBwaWQ9eW5ld3M7dz0zMDA7aD0yMDA7cT0xMDA-/https://s.yimg.com/xe/i/us/sp/v/nba_cutout/players_l/20130626/3735.png";
                break;

            //eddie house
            case 2067:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/348.png&w=350&h=254";
                break;

            //daequan cook
            case 201161:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/3196.png&w=350&h=254";
                break;

            //quentin richardson
            case 2047:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/703.png";
                break;

            //malik rose
            case 990:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Malik-Rose-2K-550x401.png";
                break;

            //mickael pietrus
            case 2554:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2173.png";
                break;

            //kurt thomas
            case 703:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/846.png";
                break;

            //spud webb
            case 892:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Spud-Webb-2K.png";
                break;
            //glen davis
            case 201175:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/3200.png";
                break;
            //sean elliot
            case 251:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Sean-Elliott-2K.png";
                break;
            //deshawn stevenson
            case 2052:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/808.png";
                break;
            //ronnie brewer
            case 200758:
                urlImage = "https://a2.espncdn.com/combiner/i?img=%2Fi%2Fheadshots%2Fnba%2Fplayers%2Ffull%2F2991.png";
                break;
            //sabonis
            case 717:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Arvydas-Sabonis-2K.png";
                break;
            //tom gugliotta
            case 339:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Tom-Gugliotta-2K.png";
                break;
            //muggsy bogues
            case 177:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Muggsy-Bogues-2K.png";
                break;
            //isaiah thomas
            case 78318:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Isiah-Thomas-2K.png";
                break;
            //scott skiles
            case 101:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Scott-Skiles-2K.png";
                break;
            //tim hardaway sr
            case 896:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Tim-Hardaway-2K.png";
                break;
            //larry johnson
            case 913:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Larry-Johnson-2K.png";
                break;
            //petrovic
            case 77845:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Drazen-Petrovic-2K.png";
                break;
            //mark jackson
            case 349:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Mark-Jackson-2K-1.png";
                break;
            //sprewell
            case 84:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Latrell-Sprewell-2K.png";
                break;
            //glen rice
            case 779:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Glen-Rice-2K.png";
                break;

            //Schrempf
            case 96:
                urlImage = "https://www.2kratings.com/wp-content/uploads/Detlef-Schrempf-2K.png";
                break;
            //david robinson
            case 764:
                urlImage = "https://webjolunba.com/caras/david_robinson.png";
                break;
            //steve nash
            case 959:
                urlImage = "https://i.dlpng.com/static/png/219514_preview.png";
                break;

            //derek fisher
            case 965:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/246.png";
                break;

            //shane battier
            case 2203:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/976.png";
                break;

            //michael redd
            case 2072:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/692.png";
                break;

            //jason kidd
            case 467:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/429.png";
                break;

            //Ray allen
            case 951:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/9.png";
                break;

            //jamison
            case 1712:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/385.png";
                break;

            //jason stackhouse
            case 711:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/802.png";
                break;

            //jermaine
            case 979:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/615.png";
                break;

            //rashard lewis
            case 1740:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/469.png";
                break;
            //juwan howard
            case 436:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/351.png";
                break;

            //jason richardson
            case 2202:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1018.png";
                break;

            //tony parker
            case 2225:
                urlImage = "https://nba-players.herokuapp.com/players/Parker/Tony";
                break;

            //chauncey billups
            case 1497:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/63.png";
                break;

            //boozer
            case 2430:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1703.png";
                break;

            //al harrington
            case 1733:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/308.png";
                break;

            //maggete
            case 1894:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/497.png";
                break;
            //odom
            case 1885:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/617.png";
                break;

            //gerald wallace
            case 2222:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1026.png";
                break;

            //brandon roy
            case 200750:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/3027.png";
                break;

            //nate robinson
            case 101126:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2782.png";
                break;
            //chris duhon
            case 2768:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2377.png";
                break;

            //rip hamilton
            case 1888:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/294.png";
                break;

            //shawn marion
            case 1890:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/510.png";
                break;

            //kirilenko
            case 1905:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/434.png";
                break;

            //andrew bynum
            case 101115:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2748.png";
                break;

            //stephen jackson
            case 1536:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/378.png";
                break;

            //francisco garcia
            case 101128:
                urlImage = "https://3.bp.blogspot.com/-bopDVYYRH2A/Uz9ibGtm3xI/AAAAAAAAXs0/ZkXJA4oq0mE/s1600/i.png";
                break;

            //ridnour
            case 2557:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/1985.png";
                break;


            default:
                urlImage = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/" + idJugador + ".png";

        }


        return urlImage;
    }

    public String checkTeamImage(String equipo) {

        String urlImage = "";

        switch (equipo) {

            case "NBA":
//                urlImage = "https://www.goodvinilos.com/6444/pegatina-logo-nba.jpg";
                urlImage = "https://cdn.bleacherreport.net/images/team_logos/328x328/nba.png";
                break;

            case "NOH":
                urlImage = "https://i.pinimg.com/originals/77/6a/3a/776a3a8005d7c176db429f7ce54367f9.png";
                break;
            case "NOK":
                urlImage = "https://i.pinimg.com/originals/77/6a/3a/776a3a8005d7c176db429f7ce54367f9.png";
                break;

            case "SEA":
                urlImage = "https://upload.wikimedia.org/wikipedia/en/thumb/a/a4/Seattle_SuperSonics_logo.svg/1200px-Seattle_SuperSonics_logo.svg.png";
                break;

            case "VAN":
                urlImage = "https://upload.wikimedia.org/wikipedia/en/thumb/1/1e/Vancouver_Grizzlies_logo.svg/1200px-Vancouver_Grizzlies_logo.svg.png";
                break;

            case "CIN":
                urlImage = "https://worldsportlogos.com/wp-content/uploads/2019/07/Cincinnati-Royals-emblem-1972.png";
                break;

            case "NJN":
                urlImage = "https://vignette.wikia.nocookie.net/baloncesto/images/8/88/New_Jersey_Nets_logo.png";
                break;

            default:
                urlImage = "https://a.espncdn.com/i/teamlogos/nba/500/" + equipo + ".png";

        }


        return urlImage;
    }

    public String getRandomAvatar() {

        ArrayList<String> images = new ArrayList<String>();

        ArrayList<NBAPlayer> nbaPlayers = new ArrayList<>();

        nbaPlayers.add(new NBAPlayer(1, "James Harden", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612745/2019/260x190/201935.png"));
        nbaPlayers.add(new NBAPlayer(2, "Giannis Antetokoumpo", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612749/2019/260x190/203507.png"));
        nbaPlayers.add(new NBAPlayer(3, "Damian Lillard", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612757/2019/260x190/203081.png"));
        nbaPlayers.add(new NBAPlayer(4, "Lebron James", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2019/260x190/2544.png"));
        nbaPlayers.add(new NBAPlayer(5, "Stephen Curry", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612744/2019/260x190/201939.png"));
        nbaPlayers.add(new NBAPlayer(6, "Luka Doncic", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612742/2019/260x190/1629029.png"));
        nbaPlayers.add(new NBAPlayer(7, "Anthony Davis", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2019/260x190/203076.png"));
        nbaPlayers.add(new NBAPlayer(8, "Steve Nash", "https://i.dlpng.com/static/png/219514_preview.png"));
        nbaPlayers.add(new NBAPlayer(9, "Tim Duncan", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612759/2015/260x190/1495.png"));
        nbaPlayers.add(new NBAPlayer(10, "Michael Jordan", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/893.png"));
        nbaPlayers.add(new NBAPlayer(11, "Kevin Durant", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612751/2019/260x190/201142.png"));
        nbaPlayers.add(new NBAPlayer(12, "Kyrie Irving", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612751/2019/260x190/202681.png"));
        nbaPlayers.add(new NBAPlayer(13, "Kobe Bryant", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2015/260x190/977.png"));
        nbaPlayers.add(new NBAPlayer(14, "Joel Embiid", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612755/2019/260x190/203954.png"));
        nbaPlayers.add(new NBAPlayer(15, "Dwyane Wade", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612748/2018/260x190/2548.png"));
        nbaPlayers.add(new NBAPlayer(16, "Chris Paul", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612760/2019/260x190/101108.png"));
        nbaPlayers.add(new NBAPlayer(17, "Zion Williamson", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612740/2019/260x190/1629627.png"));
        nbaPlayers.add(new NBAPlayer(18, "Karl Anthony Towns", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612750/2019/260x190/1626157.png"));
        nbaPlayers.add(new NBAPlayer(19, "Jimmy Butler", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612748/2019/260x190/202710.png"));
        nbaPlayers.add(new NBAPlayer(20, "Nikola Jokic", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612743/2019/260x190/203999.png"));
        nbaPlayers.add(new NBAPlayer(21, "Paul George", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612746/2019/260x190/202331.png"));
        nbaPlayers.add(new NBAPlayer(22, "Shaquille O´Neal", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612746/2019/260x190/202695.png"));
        nbaPlayers.add(new NBAPlayer(23, "Kawhi Leonard", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612746/2019/260x190/202695.png"));
        nbaPlayers.add(new NBAPlayer(24, "Klay Thompson", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612744/2019/260x190/202691.png"));
        nbaPlayers.add(new NBAPlayer(25, "Donovan Mitchell", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612762/2019/260x190/1628378.png"));
        nbaPlayers.add(new NBAPlayer(26, "Buddy Hield", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612758/2019/260x190/1627741.png"));
        nbaPlayers.add(new NBAPlayer(27, "Pau Gasol", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612749/2018/260x190/2200.png"));
        nbaPlayers.add(new NBAPlayer(28, "Peja Stojakovic", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/978.png"));
        nbaPlayers.add(new NBAPlayer(29, "Chris Bosh", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/2547.png"));
        nbaPlayers.add(new NBAPlayer(30, "Metta World Peace", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2016/260x190/1897.png"));
        nbaPlayers.add(new NBAPlayer(31, "Larry Bird", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/1449.png"));
        nbaPlayers.add(new NBAPlayer(32, "Dennis Rodman", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/23.png"));
        nbaPlayers.add(new NBAPlayer(33, "Karl Malone", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/252.png"));
        nbaPlayers.add(new NBAPlayer(34, "Allen Iverson", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/947.png"));
        nbaPlayers.add(new NBAPlayer(35, "Blake Griffin", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612765/2019/260x190/201933.png"));
        nbaPlayers.add(new NBAPlayer(36, "Tony Parker", "https://nba-players.herokuapp.com/players/Parker/Tony"));
        nbaPlayers.add(new NBAPlayer(37, "Manu Ginobili", "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612759/2017/260x190/1938.png"));

//
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612745/2019/260x190/201935.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612749/2019/260x190/203507.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612757/2019/260x190/203081.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2019/260x190/2544.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612744/2019/260x190/201939.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612742/2019/260x190/1629029.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2019/260x190/203076.png");
//        images.add("https://i.dlpng.com/static/png/219514_preview.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612759/2015/260x190/1495.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/893.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612742/2018/260x190/1717.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612751/2019/260x190/201142.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612751/2019/260x190/202681.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2015/260x190/977.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612755/2019/260x190/203954.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612746/2019/260x190/202695.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612746/2019/260x190/202331.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612743/2019/260x190/203999.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612748/2019/260x190/202710.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612750/2019/260x190/1626157.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612740/2019/260x190/1629627.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612760/2019/260x190/101108.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612748/2018/260x190/2548.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/406.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612744/2019/260x190/202691.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612762/2019/260x190/1628378.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612758/2019/260x190/1627741.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612749/2018/260x190/2200.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/1710.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/978.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/2547.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2016/260x190/1897.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/1449.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/23.png");
//        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/252.png");


        Random r = new Random();
        int low = 0;
        int high = 36;
        int result = r.nextInt(high - low) + low;

        return nbaPlayers.get(result).getUrlImage();
    }


}
