/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583StatusDetailComponent } from 'app/entities/pg-8583-status/pg-8583-status-detail.component';
import { Pg8583Status } from 'app/shared/model/pg-8583-status.model';

describe('Component Tests', () => {
  describe('Pg8583Status Management Detail Component', () => {
    let comp: Pg8583StatusDetailComponent;
    let fixture: ComponentFixture<Pg8583StatusDetailComponent>;
    const route = ({ data: of({ pg8583Status: new Pg8583Status(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583StatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(Pg8583StatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Pg8583StatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pg8583Status).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
