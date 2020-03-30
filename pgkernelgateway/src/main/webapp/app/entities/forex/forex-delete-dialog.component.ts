import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IForex } from 'app/shared/model/forex.model';
import { ForexService } from './forex.service';

@Component({
  selector: 'jhi-forex-delete-dialog',
  templateUrl: './forex-delete-dialog.component.html'
})
export class ForexDeleteDialogComponent {
  forex: IForex;

  constructor(protected forexService: ForexService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.forexService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'forexListModification',
        content: 'Deleted an forex'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-forex-delete-popup',
  template: ''
})
export class ForexDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ forex }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ForexDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.forex = forex;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/forex', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/forex', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
