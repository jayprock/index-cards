import { IndexCard } from './index-card';

export interface StudyGuide {
    studyGuideId?: number;
    studyGuideName: string;
    description: string;
    categories: string[];
    flashCards: IndexCard[];
}
