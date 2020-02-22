/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakis
 */
public class Utils {
    

    public int CalculPuntuacio (int numParticipantsPotencials,
                                 int numRespostes,
                                 int numEncertants){
        
        final float[] ESCALA_PARTICIPANTS = {0.95f, 0.75f, 0.6f, 0.41f, 0.26f, 
                                            0.06f, 0f};
        final float[] ESCALA_ENCERTANTS = {0.3f, 0.06f, 0f};
        
        final int[][] PUNTUACIONS = {{ 1,  3,  4,  5,  6,  8, 10},
                                     { 2,  4,  7,  8,  9, 13, 15},
                                     { 2,  5,  8, 10, 12, 14, 20}};
        
        final float ratioParticipants = numRespostes / numParticipantsPotencials;
        final float ratioEncertants = numEncertants / numRespostes;
        
        int participants=0;
        for (int i=0; i < ESCALA_PARTICIPANTS.length; i++) {
            if (ratioParticipants >= ESCALA_PARTICIPANTS[i]) {
                participants = i;
                break;
            }
        }
        
        int encertants=0;
        for (int i=0; i < ESCALA_ENCERTANTS.length; i++) {
            if (ratioParticipants >= ESCALA_ENCERTANTS[i]) {
                encertants = i;
                break;
            }
        }
        
        return PUNTUACIONS[participants][encertants];
    }
    
    public void main(String[] args) {
        
    }
    
}
