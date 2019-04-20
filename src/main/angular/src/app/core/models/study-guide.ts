import { IndexCard } from './index-card';

export interface StudyGuide {
    studyGuideId?: number;
    name: string;
    description: string;
    indexCards: IndexCard[];
}
