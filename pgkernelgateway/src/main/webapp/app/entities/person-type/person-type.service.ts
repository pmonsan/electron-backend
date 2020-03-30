import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonType } from 'app/shared/model/person-type.model';

type EntityResponseType = HttpResponse<IPersonType>;
type EntityArrayResponseType = HttpResponse<IPersonType[]>;

@Injectable({ providedIn: 'root' })
export class PersonTypeService {
  public resourceUrl = SERVER_API_URL + 'api/person-types';

  constructor(protected http: HttpClient) {}

  create(personType: IPersonType): Observable<EntityResponseType> {
    return this.http.post<IPersonType>(this.resourceUrl, personType, { observe: 'response' });
  }

  update(personType: IPersonType): Observable<EntityResponseType> {
    return this.http.put<IPersonType>(this.resourceUrl, personType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
