package com.sectors.sectorsbackend;

import com.sectors.sectorsbackend.domain.Sector;
import com.sectors.sectorsbackend.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final SectorRepository sectorRepository;

    @Autowired
    public DataLoader(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    // Loads in the sectors into the database
    @Override
    public void run(ApplicationArguments args) {
        Sector manufacturing = sectorRepository.save(new Sector(null, "Manufacturing"));
        sectorRepository.save(new Sector(manufacturing, "Construction materials"));
        sectorRepository.save(new Sector(manufacturing, "Electronics and Optics"));
        Sector foodAndBeverage = sectorRepository.save(new Sector(manufacturing, "Food and Beverage"));
        sectorRepository.save(new Sector(foodAndBeverage, "Bakery & confectionery products"));
        sectorRepository.save(new Sector(foodAndBeverage, "Beverages"));
        sectorRepository.save(new Sector(foodAndBeverage, "Fish & fish products"));
        sectorRepository.save(new Sector(foodAndBeverage, "Meat & meat products"));
        sectorRepository.save(new Sector(foodAndBeverage, "Milk & dairy products"));
        sectorRepository.save(new Sector(foodAndBeverage, "Other"));
        sectorRepository.save(new Sector(foodAndBeverage, "Sweets & snack food"));
        Sector furniture = sectorRepository.save(new Sector(manufacturing, "Furniture"));
        sectorRepository.save(new Sector(furniture, "Bathroom/sauna"));
        sectorRepository.save(new Sector(furniture, "Bedroom"));
        sectorRepository.save(new Sector(furniture, "Children’s room"));
        sectorRepository.save(new Sector(furniture, "Kitchen"));
        sectorRepository.save(new Sector(furniture, "Living room"));
        sectorRepository.save(new Sector(furniture, "Office"));
        sectorRepository.save(new Sector(furniture, "Other (Furniture)"));
        sectorRepository.save(new Sector(furniture, "Outdoor"));
        sectorRepository.save(new Sector(furniture, "Project furniture"));
        Sector machinery = sectorRepository.save(new Sector(manufacturing, "Machinery"));
        sectorRepository.save(new Sector(machinery, "Machinery components"));
        sectorRepository.save(new Sector(machinery, "Machinery equipment/tools"));
        sectorRepository.save(new Sector(machinery, "Manufacture of machinery"));
        Sector maritime = sectorRepository.save(new Sector(machinery, "Maritime"));
        sectorRepository.save(new Sector(maritime, "Aluminium and steel workboats"));
        sectorRepository.save(new Sector(maritime, "Boat/Yacht building"));
        sectorRepository.save(new Sector(maritime, "Ship repair and conversion"));
        sectorRepository.save(new Sector(machinery, "Metal structures"));
        sectorRepository.save(new Sector(machinery, "Other"));
        sectorRepository.save(new Sector(machinery, "Repair and maintenance service"));
        Sector metalworking = sectorRepository.save(new Sector(manufacturing, "Metalworking"));
        sectorRepository.save(new Sector(metalworking, "Construction of metal structures"));
        sectorRepository.save(new Sector(metalworking, "Houses and buildings"));
        sectorRepository.save(new Sector(metalworking, "Metal products"));
        Sector metalworks = sectorRepository.save(new Sector(metalworking, "Metal works"));
        sectorRepository.save(new Sector(metalworks, "CNC-machining"));
        sectorRepository.save(new Sector(metalworks, "Forgings, Fasteners"));
        sectorRepository.save(new Sector(metalworks, "Gas, Plasma, Laser cutting"));
        sectorRepository.save(new Sector(metalworks, "MIG, TIG, Aluminum welding"));
        Sector plasticAndRubber = sectorRepository.save(new Sector(manufacturing, "Plastic and Rubber"));
        sectorRepository.save(new Sector(plasticAndRubber, "Packaging"));
        sectorRepository.save(new Sector(plasticAndRubber, "Plastic goods"));
        Sector plasticProcessingTechnology = sectorRepository.save(new Sector(plasticAndRubber, "Plastic processing technology"));
        sectorRepository.save(new Sector(plasticProcessingTechnology, "Blowing"));
        sectorRepository.save(new Sector(plasticProcessingTechnology, "Moulding"));
        sectorRepository.save(new Sector(plasticProcessingTechnology, "Plastics welding and processing"));
        sectorRepository.save(new Sector(plasticAndRubber, "Plastic profiles"));
        Sector printing = sectorRepository.save(new Sector(manufacturing, "Printing"));
        sectorRepository.save(new Sector(printing, "Advertising"));
        sectorRepository.save(new Sector(printing, "Book/Periodicals printing"));
        sectorRepository.save(new Sector(printing, "Labelling and packaging printing"));
        Sector textileAndClothing = sectorRepository.save(new Sector(manufacturing, "Textile and Clothing"));
        sectorRepository.save(new Sector(textileAndClothing, "Clothing"));
        sectorRepository.save(new Sector(textileAndClothing, "Textile"));
        Sector wood = sectorRepository.save(new Sector(manufacturing, "Wood"));
        sectorRepository.save(new Sector(wood, "Other (Wood)"));
        sectorRepository.save(new Sector(wood, "Wooden building materials"));
        sectorRepository.save(new Sector(wood, "Wooden houses"));
        Sector other = sectorRepository.save(new Sector(null, "Other"));
        sectorRepository.save(new Sector(other, "Creative industries"));
        sectorRepository.save(new Sector(other, "Energy technology"));
        sectorRepository.save(new Sector(other, "Environment"));
        Sector service = sectorRepository.save(new Sector(null, "Service"));
        sectorRepository.save(new Sector(service, "Business services"));
        sectorRepository.save(new Sector(service, "Engineering"));
        Sector itAndTelecommunications = sectorRepository.save(new Sector(service, "Information Technology and Telecommunications"));
        sectorRepository.save(new Sector(itAndTelecommunications, "Data processing, Web portals, E-marketing"));
        sectorRepository.save(new Sector(itAndTelecommunications, "Programming, Consultancy"));
        sectorRepository.save(new Sector(itAndTelecommunications, "Software, Hardware"));
        sectorRepository.save(new Sector(itAndTelecommunications, "Telecommunications"));
        sectorRepository.save(new Sector(service, "Tourism"));
        sectorRepository.save(new Sector(service, "Translation services"));
        Sector transportAndLogistics = sectorRepository.save(new Sector(service, "Transport and Logistics"));
        sectorRepository.save(new Sector(transportAndLogistics, "Air"));
        sectorRepository.save(new Sector(transportAndLogistics, "Rail"));
        sectorRepository.save(new Sector(transportAndLogistics, "Road"));
        sectorRepository.save(new Sector(transportAndLogistics, "Water"));
    }
}
