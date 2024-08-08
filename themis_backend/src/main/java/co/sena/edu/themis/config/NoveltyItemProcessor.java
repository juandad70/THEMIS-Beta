package co.sena.edu.themis.config;

import co.sena.edu.themis.Entity.Novelty;
import org.springframework.batch.item.ItemProcessor;

public class NoveltyItemProcessor implements ItemProcessor<Novelty, Novelty> {

    @Override
    public Novelty process(Novelty novelty) throws Exception {
        // Aquí puedes implementar la lógica de procesamiento
        // Por ejemplo, cambiar el estado de la novedad
        if ("PENDING".equals(novelty.getStatus())) {
            novelty.setStatus("PROCESSED");
        }
        return novelty;
    }
}