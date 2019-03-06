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

}
