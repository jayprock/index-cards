import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StudyGuide } from '../models/study-guide';
import { StudyGuideService } from '../services/study-guide.service';

@Injectable({
    providedIn: 'root'
  })
  export class StudyGuideResolverService implements Resolve<StudyGuide> {
  
    constructor(private studyGuideService: StudyGuideService) { }
  
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StudyGuide> {
      const studyGuideId = route.paramMap.get('studyGuideId');
      return this.studyGuideService.findStudyGuide(studyGuideId);
    }
  }