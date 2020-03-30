import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonGender } from 'app/shared/model/person-gender.model';

type EntityResponseType = HttpResponse<IPersonGender>;
type EntityArrayResponseType = HttpResponse<IPersonGender[]>;

@Injectable({ providedIn: 'root' })
export class PersonGenderService {
  public resourceUrl = SERVER_API_URL + 'api/person-genders';

  constructor(protected http: HttpClient) {}

  create(personGender: IPersonGender): Observable<EntityResponseType> {
    return this.http.post<IPersonGender>(this.resourceUrl, personGender, { observe: 'response' });
  }

  update(personGender: IPersonGender): Observable<EntityResponseType> {
    return this.http.put<IPersonGender>(this.resourceUrl, personGender, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonGender>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonGender[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
