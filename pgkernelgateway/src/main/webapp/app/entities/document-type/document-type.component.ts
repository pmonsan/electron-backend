import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDocumentType } from 'app/shared/model/document-type.model';
import { AccountService } from 'app/core';
import { DocumentTypeService } from './document-type.service';

@Component({
  selector: 'jhi-document-type',
  templateUrl: './document-type.component.html'
})
export class DocumentTypeComponent implements OnInit, OnDestroy {
  documentTypes: IDocumentType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected documentTypeService: DocumentTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.documentTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IDocumentType[]>) => res.ok),
        map((res: HttpResponse<IDocumentType[]>) => res.body)
      )
      .subscribe(
        (res: IDocumentType[]) => {
          this.documentTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDocumentTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDocumentType) {
    return item.id;
  }

  registerChangeInDocumentTypes() {
    this.eventSubscriber = this.eventManager.subscribe('documentTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
