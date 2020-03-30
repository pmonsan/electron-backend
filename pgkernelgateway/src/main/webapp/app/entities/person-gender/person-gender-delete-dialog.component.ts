import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonGender } from 'app/shared/model/person-gender.model';
import { PersonGenderService } from './person-gender.service';

@Component({
  selector: 'jhi-person-gender-delete-dialog',
  templateUrl: './person-gender-delete-dialog.component.html'
})
export class PersonGenderDeleteDialogComponent {
  personGender: IPersonGender;

  constructor(
    protected personGenderService: PersonGenderService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.personGenderService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'personGenderListModification',
        content: 'Deleted an personGender'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-person-gender-delete-popup',
  template: ''
})
export class PersonGenderDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personGender }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PersonGenderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.personGender = personGender;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/person-gender', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/person-gender', { outlets: { popup: null } }]);
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
