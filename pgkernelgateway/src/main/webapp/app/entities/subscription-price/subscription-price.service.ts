import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISubscriptionPrice } from 'app/shared/model/subscription-price.model';

type EntityResponseType = HttpResponse<ISubscriptionPrice>;
type EntityArrayResponseType = HttpResponse<ISubscriptionPrice[]>;

@Injectable({ providedIn: 'root' })
export class SubscriptionPriceService {
  public resourceUrl = SERVER_API_URL + 'api/subscription-prices';

  constructor(protected http: HttpClient) {}

  create(subscriptionPrice: ISubscriptionPrice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionPrice);
    return this.http
      .post<ISubscriptionPrice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subscriptionPrice: ISubscriptionPrice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionPrice);
    return this.http
      .put<ISubscriptionPrice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISubscriptionPrice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubscriptionPrice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(subscriptionPrice: ISubscriptionPrice): ISubscriptionPrice {
    const copy: ISubscriptionPrice = Object.assign({}, subscriptionPrice, {
      modificationDate:
        subscriptionPrice.modificationDate != null && subscriptionPrice.modificationDate.isValid()
          ? subscriptionPrice.modificationDate.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.modificationDate = res.body.modificationDate != null ? moment(res.body.modificationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((subscriptionPrice: ISubscriptionPrice) => {
        subscriptionPrice.modificationDate = subscriptionPrice.modificationDate != null ? moment(subscriptionPrice.modificationDate) : null;
      });
    }
    return res;
  }
}
