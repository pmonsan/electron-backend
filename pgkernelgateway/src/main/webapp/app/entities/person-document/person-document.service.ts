import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonDocument } from 'app/shared/model/person-document.model';

type EntityResponseType = HttpResponse<IPersonDocument>;
type EntityArrayResponseType = HttpResponse<IPersonDocument[]>;

@Injectable({ providedIn: 'root' })
export class PersonDocumentService {
  public resourceUrl = SERVER_API_URL + 'api/person-documents';

  constructor(protected http: HttpClient) {}

  create(personDocument: IPersonDocument): Observable<EntityResponseType> {
    return this.http.post<IPersonDocument>(this.resourceUrl, personDocument, { observe: 'response' });
  }

  update(personDocument: IPersonDocument): Observable<EntityResponseType> {
    return this.http.put<IPersonDocument>(this.resourceUrl, personDocument, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
