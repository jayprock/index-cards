import { IndexCard } from './index-card';

export interface StudyGuide {
    studyGuideId?: number;
    studyGuideName: string;
    description: string;
    flashCards: IndexCard[];
}
