import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPriceCommission } from 'app/shared/model/price-commission.model';

type EntityResponseType = HttpResponse<IPriceCommission>;
type EntityArrayResponseType = HttpResponse<IPriceCommission[]>;

@Injectable({ providedIn: 'root' })
export class PriceCommissionService {
  public resourceUrl = SERVER_API_URL + 'api/price-commissions';

  constructor(protected http: HttpClient) {}

  create(priceCommission: IPriceCommission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(priceCommission);
    return this.http
      .post<IPriceCommission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(priceCommission: IPriceCommission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(priceCommission);
    return this.http
      .put<IPriceCommission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPriceCommission>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPriceCommission[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(priceCommission: IPriceCommission): IPriceCommission {
    const copy: IPriceCommission = Object.assign({}, priceCommission, {
      dateCreation:
        priceCommission.dateCreation != null && priceCommission.dateCreation.isValid() ? priceCommission.dateCreation.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreation = res.body.dateCreation != null ? moment(res.body.dateCreation) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((priceCommission: IPriceCommission) => {
        priceCommission.dateCreation = priceCommission.dateCreation != null ? moment(priceCommission.dateCreation) : null;
      });
    }
    return res;
  }
}
