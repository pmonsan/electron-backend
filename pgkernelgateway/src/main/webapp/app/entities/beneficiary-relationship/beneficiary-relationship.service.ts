import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBeneficiaryRelationship } from 'app/shared/model/beneficiary-relationship.model';

type EntityResponseType = HttpResponse<IBeneficiaryRelationship>;
type EntityArrayResponseType = HttpResponse<IBeneficiaryRelationship[]>;

@Injectable({ providedIn: 'root' })
export class BeneficiaryRelationshipService {
  public resourceUrl = SERVER_API_URL + 'api/beneficiary-relationships';

  constructor(protected http: HttpClient) {}

  create(beneficiaryRelationship: IBeneficiaryRelationship): Observable<EntityResponseType> {
    return this.http.post<IBeneficiaryRelationship>(this.resourceUrl, beneficiaryRelationship, { observe: 'response' });
  }

  update(beneficiaryRelationship: IBeneficiaryRelationship): Observable<EntityResponseType> {
    return this.http.put<IBeneficiaryRelationship>(this.resourceUrl, beneficiaryRelationship, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBeneficiaryRelationship>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBeneficiaryRelationship[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
