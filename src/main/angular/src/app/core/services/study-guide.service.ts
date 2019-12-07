import { HttpClient, HttpParams } from '@angular/common/http'

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { REST_PATHS } from '../../rest-paths';
import { StudyGuide } from '../models/study-guide';

@Injectable({
  providedIn: 'root'
})
export class StudyGuideService {

  constructor(private http: HttpClient) { }

  findStudyGuide(id: string): Observable<StudyGuide> {
    return this.http.get<StudyGuide>(`${REST_PATHS.studyGuides}/${id}`);
  }

  createStudyGuide(studyGuide: StudyGuide): Observable<StudyGuide> {
    return this.http.post<StudyGuide>(`${REST_PATHS.studyGuides}`, studyGuide);
  }

  updateStudyGuide(studyGuide: StudyGuide): Observable<StudyGuide> {
    return this.http.put<StudyGuide>(`${REST_PATHS.studyGuides}`, studyGuide);
  }

  searchStudyGuide(param: string): Observable<StudyGuide[]> {
    return this.http.get<StudyGuide[]>(`${REST_PATHS.studyGuides}?search=${param}`);
  }

}
