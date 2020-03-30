import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDetailContract } from 'app/shared/model/detail-contract.model';

type EntityResponseType = HttpResponse<IDetailContract>;
type EntityArrayResponseType = HttpResponse<IDetailContract[]>;

@Injectable({ providedIn: 'root' })
export class DetailContractService {
  public resourceUrl = SERVER_API_URL + 'api/detail-contracts';

  constructor(protected http: HttpClient) {}

  create(detailContract: IDetailContract): Observable<EntityResponseType> {
    return this.http.post<IDetailContract>(this.resourceUrl, detailContract, { observe: 'response' });
  }

  update(detailContract: IDetailContract): Observable<EntityResponseType> {
    return this.http.put<IDetailContract>(this.resourceUrl, detailContract, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetailContract>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetailContract[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
