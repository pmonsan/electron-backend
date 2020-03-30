/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LoanInstalmentStatusDetailComponent } from 'app/entities/loan-instalment-status/loan-instalment-status-detail.component';
import { LoanInstalmentStatus } from 'app/shared/model/loan-instalment-status.model';

describe('Component Tests', () => {
  describe('LoanInstalmentStatus Management Detail Component', () => {
    let comp: LoanInstalmentStatusDetailComponent;
    let fixture: ComponentFixture<LoanInstalmentStatusDetailComponent>;
    const route = ({ data: of({ loanInstalmentStatus: new LoanInstalmentStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LoanInstalmentStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LoanInstalmentStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LoanInstalmentStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.loanInstalmentStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
