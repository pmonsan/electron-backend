/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageStatusDetailComponent } from 'app/entities/pg-message-status/pg-message-status-detail.component';
import { PgMessageStatus } from 'app/shared/model/pg-message-status.model';

describe('Component Tests', () => {
  describe('PgMessageStatus Management Detail Component', () => {
    let comp: PgMessageStatusDetailComponent;
    let fixture: ComponentFixture<PgMessageStatusDetailComponent>;
    const route = ({ data: of({ pgMessageStatus: new PgMessageStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgMessageStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgMessageStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgMessageStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
