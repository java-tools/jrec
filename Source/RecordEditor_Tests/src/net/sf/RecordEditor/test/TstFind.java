package net.sf.RecordEditor.test;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.Compare;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;
import junit.framework.TestCase;

public class TstFind extends TestCase {
	private int dbIdx = TstConstants.DB_INDEX;

	private CopyBookDbReader copybookInt = new CopyBookDbReader();
    private static LayoutDetail storeLayout = null;

	private String storeCopybook =  "ams Store";

	private FileView storeFile;

	 private final String[] textLines = {
			 "TAR5839DCDC - Taras Ave                                                             30-68 Taras Ave                         Altona North                       3025      VICA",
			 "TAR5850DCVIC West Ad Support                                                        Lot 2 Little Boundary Rd                Laverton                           3028      VICA",
			 "TAR5853DCNSW North Sydney Ad Support                                                                                                                                        A",
			 "TAR5866DCWA Ad Support                                                                                                                                                      A",
			 "TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA",
			 "TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA",
			 "TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA",
			 "TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA",
			 "TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA",
			 "TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA",
			 "TAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA",
			 "TAR5060STSt Marys                           St. Mary's                              Charles Hackett Drive                   St Mary's                          2760      NSWA",
			 "TAR5070STBass Hill                          Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA",
			 "TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA",
			 "TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA",
			 "TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA",
			 "TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA",
			 "TAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA",
			 "TAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA",
			 "TAR5093STLiverpool                          Westfield Phoenix Plaza                 Northumberland Street                   Liverpool                          2170      NSWA",
			 "TAR5095STEastgarden                         Westfield Shoppingtown Eastgardens      152 Bunnerong Road                      Eastgardens                        2036      NSWA",
			 "TAR5129STMacquarie                          Maquarie Centre                         Dock 2, Talavera Road                    North Ryde                        2113      NSWA",
			 "TAR5144STParramatta                         Parramatta                              Dock J, Aird Street                     Parramatta                         2150      NSWA",
			 "TAR5157STChirnside Park                     Kilsyth South                           Lot 3 & 4 Southfork Drive               Kilsyth Park                       3137      VICA",
			 "TAR5165STHurstville                         Condell Park                            Unit 2, 39-41 Allignham Street          Condell Park                       2200      NSWA",
			 "TAR5303STMacarthur Square                   Macarthur Square                        Gilchrist Drive                         Campbelltown                       2560      NSWA",
			 "TAR5169STHornsby                            Westfield Shopping Centre               George Street                           Hornsby                            2077      NSWA",
			 "TAR5170STBondi                              Building B, Portside Distribution Centre2-8 McPherson Street                    Botany                             2019      NSWA",
			 "TAR5171STBurwood                            Building B, Portside Distribution Centre2-8 McPherson Street                    Botany                             2019      NSWA",
			 "TAR5177STCastle Hill                        Castle Towers Shopping Centre           Cnr Eric Felton & Pennant Sts           Castlehill                         2154      NSWA",
			 "TAR5178STBlacktown - Not Yet Open           West Point Shopping Ctr                 Balmoral Street                         Blacktown                          2134      NSWA",
			 "TAR5016STGlendale                           Glendale Power Centre                   Cnr. West Wallsend & Lake Roads         Newcastle                          2300      NSWA",
			 "TAR5089STErina                              Erina Fair                              Cnr. Karalta Road & Terrigal Drive      Erina                              2250      NSWA",
			 "TAR5136STCharlestown                        Charleston Square                       Pearson Street                          Charlestown                        2290      NSWA",
			 "TAR5011STCessnock                           Cessnock City Centre                    Cnr. North Avenue & Darwin Street       Cessnock                           2325      NSWA",
			 "TAR5302STNewcastle (Closed)                 Stockland Super Centre                  Lake Road                               Glendale                           2285      NSWA",
			 "TAR5046STDubbo                              Dubbo City Centre                       Cnr. Bultje & Macquarie Streets         Dubbo                              2830      NSWA",
			 "TAR5145STPort Macquarie                     Town Centre                             Murray Street                           Port Macquarie                     2444      NSWA",
			 "TAR5096STCanberra Civic                     Target Canberra                         Canberra City Centre, Akuna Ave         Canberra                           2601      ACTA",
			 "TAR5154STShellharbour                       Shellharbour Shp Square                 Cnr. Benson Ave & East Road             Shellharbour                       2529      NSWA",
			 "TAR5162STTuggeranong                        Hyperdome Shopping Centre               Pitman Street                           Tuggernong                         2900      NSWA",
			 "TAR5163STQueanbeyan                         Riverside Plaza Shopping Centre         Cnr Monaro St & Fallick Lane            Queanbeyan                         2620      NSWA",
			 "TAR5164STTuggerah                           Target Tuggerah                         3 Craftsman Avenue                      Chittaway Point                    2261      NSWA",
			 "TAR5012STRingwood                           Ringwood                                Seymour Street                          Ringwood                           3134      VICA",
			 "TAR5030STEpping                             Epping Plaza Shopping Centre            Cnr. High & Cooper Streets              Epping                             3076      VICA",
			 "TAR5032STCamberwell                         Camberwell                              Station Street                          Camberwell                         3124      VICA",
			 "TAR5042STReservoir                          Target                                  850 Plenty Road                          East Preston                      3072      VICA",
			 "TAR5054STHighpoint City                     Laverton                                Lot 2, Cnr Lt Boundry & Old Geelong RoadLaverton                           3028      VICA",
			 "TAR5058STKnox City                          Kilsyth South                           Lots 3 & 4 Southfork Road               Kilsyth South                      3137      VICA",
			 "TAR5061STWaverley Gardens                    Mulgrave                               Cnr. Police & Jacksons Roads            Mulgrave                           3170      VICA",
			 "TAR5064STHoppers Crossing                   Laverton                                Lot 2, Cnr Lt. Boundary & Old Geelong RdLaverton                           3028      VICA",
			 "TAR5082STBox Hill                           Box Hill                                Cnr. Thurston Street & Carrington Road  Box Hill                           3128      VICA",
			 "TAR5086STSouthland                          Hampton Park, 2B Waterview Close         2B Waterview Close                     Hampton Park                       3976      VICA",
			 "TAR5087STChadstone                          Chadstone Shopping Centre               Dandenong Road                          Chadstone                          3148      VICA",
			 "TAR5088STTemplestowe                        Pines Shopping Centre                   Cnr. Blackburn & Reynolds Roads         East Doncaster                     3109      VICA",
			 "TAR5092STMalvern                            Malvern                                 110 Wattletree & Glenferrie Roads       Malvern                            3144      VICA",
			 "TAR5097STFountain Gate                      Hampton Park                            2B Waterview Close                      Hampton Park                       3976      VICA",
			 "TAR5098STFrankston                          Hampton Park                            2B Waterview Close                      Hampton Park                       3976      VICA",
			 "TAR5099STGlen Waverley                      The Glen Shopping Centre                Springvale Road                         Glen Waverly                       3150      VICA",
			 "TAR5131STMelbourne City                     Laverton                                Lot 2, Cnr. Lt Boundry & Old Geelong RdsLaverton                           3028      VICA",
			 "TAR5137STDandenong                          Kilsyth South                           Lot  3 & 4 Southfork Drive              Kilsyth                            3137      VICA",
			 "TAR5149STBroadmeadows                       Broadmeadows Town Centre                Pascoe Vale Rd                          Broadmeadows                       3047      VICA",
			 "TAR5158STBrimbank Central                   Brimbank Central Shopping Centre        Cnr. Neale & Station Roads- Deer Park   Brimbank                           3023      VICA",
			 "TAR5160STWatergardens                       Taylors Lakes                           Cnr. Kings Road & Keilor -Melton Higway Taylors Lakes                      3038      VICA",
			 "TAR5168STAirport West                       Westfield Shoppingtown                  Matthews Avenue                         Airport West                       3042      VICA",
			 "TAR5179STGreensborough                      Laverton Offsite Reserve                Lot 2 Cnr Lt Boundary & Old Geelong RoadLaverton                           3028      VICA",
			 "TAR5301STNarre Warren                       Hampton Park                            Lot 2B Waterview Close                  Hampton Park                       3976      VICA",
			 "TAR5300STWyndham Closed                     Werribee Plaza                          Heaths Road                             Werribee                           3030      VICA",
			 "TAR5304STNorthland Baby Target              Northland Shopping Centre               2-50 Murray Road                        East  Preston                      3072      VICA",
			 "TAR5152STMornington                         Mornington                              Cnr Franklin & Gordon Streets           Momington                          3931      VICA",
			 "TAR5028STLaunceston                         Launceston                              71-99 Galvin Street                     Launceston                         7250      TASA",
			 "TAR5132STGlenorchy                          Derwent Park                            Lot 12-13 Riverside Industrial Estate   Derwent park                       7009      TASA",
			 "TAR5161STHobart                             Derwent Park                            Lot 12-13 Riverside Industrial Estate   Derwent Park                       7000      TASA",
			 "TAR5067STMorwell Mid Valley                 Mid Valley Shopping Centre              Princes Highway                         Morwell                            3840      VICA",
			 "TAR5075STSale                               Gippsland Centre                        Cunningham Street                       Sale                               3850      VICA",
			 "TAR5153STPakenham                           Pakenham Place Shopping Centre          59-67  Main Street                      Pakenham                           3810      VICA",
			 "TAR5001STGeelong                            Bay City Plaza Shopping Centre          Cnr. Brougham & Moorabool Streets       Geelong                            3220      VICA",
			 "TAR5004STBallarat                           Ballarat                                Doveton Street                          Ballarat                           3350      VICA",
			 "TAR5023STWarnambool                         Warrnambool                             17 Strong Street                        Warnambool                         3280      VICA",
			 "TAR5039STColac                              Colac                                   145 Murray Street                       Colac                              3250      VICA",
			 "TAR5040STTweed Heads                        South Tweed Heads                       Unit 1, 46-52 Greenway Drive            South Tweed Heads                  2486      NSWA",
			 "TAR5100STWaurn Ponds                        Town & World Shopping World             Princes Highway                         Warn Ponds                         3216      VICA",
			 "TAR5005STAlbury                             Albury                                  Kiewa Street                            Albury                             2640      NSWA",
			 "TAR5006STWodonga                            Wodonga                                 Cnr. Elgin & Watson Streets             Wodonga                            3690      VICA",
			 "TAR5007STShepparton                         Shepparton                              High Street                             Shepparton                         3630      VICA",
			 "TAR5008STBendigo                            Bendigo                                 97 Garsed Street                        Bendigo                            3550      VICA",
			 "TAR5020STIndooroopilly                      Indooroopilly Shopping Centre           Stamford Road                           Indooroopilly                      4068      QLDA",
			 "TAR5059STBuranda                            Target Buranda                          Cnr Cornwall St and Ipwich Rd           Buranda                            4102      QLDA",
			 "TAR5068STMt Gravatt                         Mt Gravatt                              Cnr. Logan & Creek Roads                Mt Gravitt                         4122      QLDA",
			 "TAR5083STSpringwood                         Springwood                              Cnr. Arndale Stt & Fitzgerald Avenue    Springwood                         4127      QLDA",
			 "TAR5084STBrookside                          Brookside Shopping Centre               Osborne Road                            Mitchelton                         4053      QLDA",
			 "TAR5133STBrowns Plains                      Browns Plains Shopping Centre           Cnr. Mt Lindsay Hwy & Browns Plains Rd  Browns Plains                      4118      QLDA",
			 "TAR5135STCapalaba                           Capalaba Park                           Cnr. Redland Bay & Mount Cotton Road    Capalaba                           4157      QLDA",
			 "TAR5155STRedbank                            Redbank Shopping Centre                 1 Collingwood Park Drive                Redbank                            4301      QLDA",
			 "TAR5156STMorayfield                         Morayfield Shopping Centre              Morayfield                              Morayfield                         4506      QLDA",
			 "TAR5166STChermside                          Cnr: Orsova and Greg Streets                                                    Pinkenba                           4008      QLDA",
			 "TAR5167STNorthlakes                         Cnr Anzac Ave & Northlakes Drive                                                Mango Hill                         4509      QLDA",
			 "TAR5172STStrathpine - Not Yet Open          Chermside Shopping Centre, G ie Rd                                              Strathpine                         4032      QLDA",
			 "TAR5048STBundaberg                          Bundaberg                               Bourbong Street                         Bundaberg                          4670      QLDA",
			 "TAR5049STRockhampton                        Rockhampton                             Cnr. Fitzroy & Alma Streets             Rockhampton                        4700      QLDA",
			 "TAR5139STToowoomba                          Grand Central Shopping Centre           Clifford Street                         Toowoomba                          4650      QLDA",
			 "TAR5143STHervey Bay                         Bay Central Shopping Centre             Boat Harbour Drive                      Hervey Bay                         4655      OLDA",
			 "TAR5003STMackay                             Caneland Shoppingtown                   Cnr. Victoria Street & Mangrove Road    Mackay                             4740      QLDA",
			 "TAR5010STEarlville                          Earlville Shopping Centre               Mulgrave Road                           Cairns                             4870      QLDA",
			 "TAR5062STCastletown                         Townsville                              Cnr. Woolcock St. & Kings Road          Townsville                         4810      QLDA",
			 "TAR5138STCairns Central                     Cairns                                  Cnr. McLeod & Aplin Streets             Cairns                             4870      QLDA",
			 "TAR5141STThe Willows                        Thuringowa Central                      Cnr Thuringowa Drive &  Range Rd        Thuringowa Central                 4817      QLDA",
			 "TAR5146STPalmerston                         Palmerston Shopping Centre              Temple Terrace                          Palmerston                         0830      NT A",
			 "TAR5002STCoffs Harbour                      Coffs Harbour                           Cnr. Park Beach Road & Pacific Hwy      Coffs Harbour                      2450      NSWA",
			 "TAR5027STRunaway Bay                        Runaway Bay Shopping Village            Bayview Street                          Runaway Bay                        4216      QLDA",
			 "TAR5038STMaroochydore                       Sunshine Plaza                          Plaza Parade                            Maroochydore                       4588      QLDA",
			 "TAR5073STSouthport                          Southport                               Cnr. Meyers Ferry & Benowa Roads        Southport                          4215      QLDA",
			 "TAR5126STRobina                             Robina Town Centre                      Robina Parkway                          Robina                             4226      QLDA",
			 "TAR5140STPacific Fair                       Pacific Shopping Centre                 Hooker Boulevard                        Broadbeach                         4218      QLDA",
			 "TAR5009STCentrepoint                        Adelaide Airport                        Frank Collopy Court                     Adelaide                           5950      SA A",
			 "TAR5021STTea Tree Plaza                     Myer Tea Tree Plaza                     976 North East Road                     Modbury                            5092      SA A",
			 "TAR5024STNewton                             Newton                                  Gorge Road                              Newtown                            5074      SA A",
			 "TAR5025STFulham Gardens                     Fulham Gardens                          Tapileys Hill Road                      Fulham Gardens                     5024      SA A",
			 "TAR5026STEdwardstown                        Offsite Reserve                         Frank Callopy Court                     Adelaide Airport                   5950      SA A",
			 "TAR5047STSefton Park                        Adelaide Airport                        Frank Collopy Court                     Adelaide Airport                   5950      SA A",
			 "TAR5077STElizabeth                          Elizabeth Civic Centre                  Philip Highway                          Elizabeth                          5112      SA A",
			 "TAR5080STSunshine                           Sunshine                                Cnr. Hampshire Road & Service Street    Sunshine                           3020      VICA",
			 "TAR5127STMarion                             Adelaide Airport                        Frank Collopy Court                     Adelaide Airport                   5950      SA A",
			 "TAR5134STReynella                           Adelaide Airport                        Frank Collopy Court                     Adelaide Airport                   5950      SA A",
			 "TAR5142STHollywood Plaza                    Hollywood Plaza Shopping Centre         Winzor Road                             Salisbury Downs                    5108      SA A",
			 "TAR5044STMt Gambier                         Ferrers Street                                                                  Mt Gambier                         5290      SA A",
			 "TAR5071STMildura                            Mildura                                 Cnr. Deakin Avenue & Fifteenth Street   Mildura                            3500      VICA",
			 "TAR5159STWhyalla                            Westlands Shopping Centre               McDougall- Stewart Avenue               Whayalla Norrie                    5608      SA A",
			 "TAR5036STFremantle                          Fremantle                               Cnr. Queen & Adelaide Streets           Fremantle                          6160      WA A",
			 "TAR5043STMorley                             Westfield Galleria Shopping Centre      Collier Road                            Morley                             6062      WA A",
			 "TAR5045STCarousel                           Target Carousel                         1382 Albany Highway                     Cannington                         6107      WA A",
			 "TAR5057STVictoria Park                      The Park Centre                         Albany Highway                           East Victoria Park                6101      WA A",
			 "TAR5065STInnaloo                            Westfield Innaloo City Centre           Oswald Street                           Innaloo                            6018      WA A",
			 "TAR5069STMidland                            Midland Gate Shopping Centre            Brockman Road                           Midland                            6056      WA A",
			 "TAR5076STBull Creek                         Bullcreek Shopping Centre               Cnr. Benningfield Road & South Street   Bull Creek                         6155      WA A",
			 "TAR5079STStirling                           Stirling Central Shopping Centre        Wanneroo Road                           Westminister                       6061      WA A",
			 "TAR5094STWhitford City                      Whitford City Shopping Centre           Cnr. Whitfords & Marmion Avenues        Hillarys                           6065      WA A",
			 "TAR5128STRockingham                         Rockingham Shopping Centre.             Cnr. Read Street & Council Avenue       Rockingham                         6168      WA A",
			 "TAR5151STPerth                              Perth                                   Murray Street Mall                      Perth                              6000      WA A",
			 "TAR5180STJoondalup                          Lakeside Shopping Centre                420 Joondalup Drive                     Joondalup                          6027      WA A",
			 "TAR5072STGeraldton                          Geraldton                               Cnr. Chapman Road & View Street         Geraldton                          6530      WA A",
			 "TAR5173STBunbury                            Centrepoint Shopping Centre             Blair Streets                           Bunbury                            6230      WA A",
			 "TAR5700DCHead Office                                                                                                                                                        A",
			 "TAR5174STCarindale                          Carindale Shopping Centre               Creek Rd                                Carindale                          4152      QLDA",
			 "TAR5887DCQLD Ad Support                                                                                                                                                     A",
			 "TAR5888DCSA Ad Support                                                                                                                                                      A",
			 "TAR5109DCNational Ad Support                                                                                                                                                A",
			 "TAR5895DCVIC East Ad Support                                                                                                                                                A",
			 "TAR5897DCSydney Gate DC                     No 2 Sydney Gate                        830 Bourke Street                       Waterloo                           2017      NSWA",
			 "TAR5184STBrisbane CBD                       Myer Centre                             Queen Street                            Brisbane                           4000      QLDA",
			 "TAR5305STGolden Grove                       The Golden Way                          Cnr Grove Way & Golden Grove            Golden Grove                       5125      SOUA",
			 "TAR5183STSwan Hill                          Target Swan Hill                        151-165 Beveridge Street                Swan Hill                          3585      VICA",
			 "TAR5949DCCentral Returns Centre                                                     214-228 Blackshaws Rd                   Altona North                                 VICA",
			 "TAR5951DCNSW West Sydney Ad Support                                                                                                                                         A",
			 "TAR5952DCTAS Ad Support                                                                                                                                                     A",
			 "TAR5192STWetherill Park                                                                                                                                                     A",
			 "TAR5150STCowra                              74 Kendal Street                                                                Cowra                              2794      NSWA",
			 "TAR5953DCNorth Geelong Warehouse            Target Head Office                      12 Thompson Road                        North Geelong                      3215      VICA",
			 "TAR5175STTamworth                           369 Peel Street                                                                 Tamworth                           2340      NSWA",
			 "TAR5954DCState  Warehouse NSW               Target State Warehouse NSW (Westgate)   Warehouse D Murtha Street               Arndell Park                       2148      NSWA",
			 "TAR5955DCState Warehouse VIC                Target State Warehouse VIC (Patricks)   180 Fitzgerald Road                     Laverton                           3028      VICA",
			 "TAR5957DCState Warehouse SA                 Target State Warehouse  (Patricks)      180 Fitzgerald Road                     Laverton                           3028      VICA",
			 "TAR5958DCState Warehouse  WA                Target State Warehouse (WA) FCL         56 Dowd Street                          Welshpool                          6106      WA A",
			 "TAR5956DCState Warehouse QLD                Target State Warehouse QLD (RMS)        243 Bradman Street                      Acacia Ridge                       4110      QLDA",
		 
	 };
	
	/**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        int i;
        Line l;
        Common.setConnectionId(dbIdx);
        storeLayout = copybookInt.getLayout(storeCopybook);
        storeFile = new FileView(storeLayout, LineIOProvider.getInstance(), false);

       for (i = 0; i < textLines.length; i++) {
           l = new Line(storeLayout, Conversion.getBytes(textLines[i], ""));
           storeFile.add(l);
        }
    }


    /**
     * replace test 1
     * @throws Exception any error
     */
    public void testFind1() throws Exception {
    	int recId = 0;
 
        FilePosition pos = new FilePosition(0, 0, recId, 1, true);

        storeFile.find("55", pos, true, Compare.OP_TEXT_LE);
        assertTrue("Text LE not found", pos.found);
        assertEquals("Find 1, expecting 4 was " + pos.row, 4, pos.row);
        
        storeFile.find("55", pos, true, Compare.OP_TEXT_GE);
        assertTrue("Find 2a", pos.found);
        assertEquals("Find 2b, expecting 142 was " + pos.row, 142, pos.row);
        
        pos.setForward(false);
        storeFile.find("05080", pos, true, Compare.OP_NUMERIC_EQ);
        assertTrue("Find 3a", pos.found);
        assertEquals("Find 3b, expecting 121 was " + pos.row, 121, pos.row);
        
        storeFile.find("5080", pos, true, Compare.OP_NUMERIC_GT);
        assertTrue("Find 4a", pos.found);
        assertEquals("Find 4b, expecting 113 was " + pos.row, 113, pos.row);

        storeFile.find("5080", pos, true, Compare.OP_NUMERIC_LT);
        assertTrue("Find 4a", pos.found);
        assertEquals("Find 4b, expecting 111 was " + pos.row, 111, pos.row);
        
        storeFile.find("51", pos, true, Compare.OP_TEXT_GT);
        assertTrue("Find 5a", pos.found);
        assertEquals("Find 5b, expecting 107 was " + pos.row, 107, pos.row);
        
        storeFile.find("51", pos, true, Compare.OP_TEXT_LT);
        assertTrue("Find 6a", pos.found);
        assertEquals("Find 6b, expecting 104 was " + pos.row, 104, pos.row);
        
        storeFile.find("72", pos, true, Compare.OP_CONTAINS);
        assertTrue("Find 7a", pos.found);
        assertEquals("Find 7b, expecting 97 was " + pos.row, 97, pos.row);
        
        storeFile.find("5083", pos, true, Compare.OP_EQUALS);
        assertTrue("Find 8a", pos.found);
        assertEquals("Find 8b, expecting 89 was " + pos.row, 89, pos.row);

        storeFile.find("50", pos, true, Compare.OP_DOESNT_CONTAIN);
        assertTrue("Find 9a", pos.found);
        assertEquals("Find 8b, expecting 81 was " + pos.row, 81, pos.row);
    }
    
    
    public void testFind2() throws Exception {
    	int recId = 0;
 
        FilePosition pos = new FilePosition(0, 0, recId, 1, true);

        storeFile.find("5800", pos, true, Compare.OP_NUMERIC_LT);
        assertTrue("Text 21a", pos.found);
        assertEquals("Find 21b, expecting 4 was " + pos.row, 4, pos.row);

        storeFile.find("5060", pos, true, Compare.OP_NUMERIC_EQ);
        assertTrue("Text 22a", pos.found);
        assertEquals("Find 22b, expecting 11 was " + pos.row, 11, pos.row);

        storeFile.find("5081", pos, true, Compare.OP_NUMERIC_GE);
        assertTrue("Text 23a", pos.found);
        assertEquals("Find 23b, expecting 15 was " + pos.row, 15, pos.row);

        storeFile.find("5100", pos, true, Compare.OP_NUMERIC_GT);
        assertTrue("Text 24a", pos.found);
        assertEquals("Find 24b, expecting 21 was " + pos.row, 21, pos.row);

        storeFile.find("5303", pos, true, Compare.OP_NUMERIC_GE);
        assertTrue("Text 25a", pos.found);
        assertEquals("Find 25b, expecting 25 was " + pos.row, 25, pos.row);

        storeFile.find("5100", pos, true, Compare.OP_NUMERIC_LT);
        assertTrue("Text 26a", pos.found);
        assertEquals("Find 26b, expecting 31 was " + pos.row, 31, pos.row);

        storeFile.find("5011", pos, true, Compare.OP_NUMERIC_LE);
        assertTrue("Text 27a", pos.found);
        assertEquals("Find 27b, expecting 34 was " + pos.row, 34, pos.row);

        storeFile.find("51", pos, true, Compare.OP_CONTAINS);
        assertTrue("Text 28a", pos.found);
        assertEquals("Find 28b, expecting 37 was " + pos.row, 37, pos.row);

        pos.adjustPosition(1, Compare.OP_CONTAINS);
        storeFile.find("51", pos, true, Compare.OP_CONTAINS);
        assertTrue("Text 29a", pos.found);
        assertEquals("Find 29b, expecting 39 was " + pos.row, 39, pos.row);

        storeFile.find("51", pos, true, Compare.OP_DOESNT_CONTAIN);
        assertTrue("Text 2Aa", pos.found);
        assertEquals("Find 2Ab, expecting 43 was " + pos.row, 43, pos.row);
        System.out.println("==> " +pos.found + " " + pos.row + " " + pos.currentFieldNumber);

    }
}
