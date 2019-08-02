import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { REST_PATHS } from 'src/app/rest-paths';

@Injectable({
    providedIn: 'root'
})
export class StudyGuideCategoryService {

    constructor(private http: HttpClient) {}

    search(param: string, exclusions ?: string[]): Observable<string[]> {
        if (exclusions && exclusions.length > 0) {
            return this.http.get<string[]>(`${REST_PATHS.studyGuideCategories}?search=${param}&exclude=${exclusions.join()}`);
        } else {
            return this.http.get<string[]>(`${REST_PATHS.studyGuideCategories}?search=${param}`);
        }
    }
}